package notblank.boatvote.domain.survey.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.survey.dto.internal.FilterDTO;
import notblank.boatvote.domain.survey.dto.internal.SurveyInfoDTO;
import notblank.boatvote.domain.survey.dto.request.CreateSurveyRequest;
import notblank.boatvote.domain.survey.dto.internal.OptionDTO;
import notblank.boatvote.domain.survey.dto.internal.QuestionDTO;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.survey.dto.response.SurveyResponse;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.survey.repository.SurveyRepository;
import notblank.boatvote.domain.utility.CodeConverter;
import notblank.boatvote.domain.utility.DTOConverter;
import notblank.boatvote.domain.user.entity.User;
import notblank.boatvote.domain.user.service.UserService;
import notblank.boatvote.global.exception.errorcode.SurveyErrorCode;
import notblank.boatvote.global.exception.exception.SurveyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final UserService userService;

    private final SurveyRepository surveyRepository;

    private final CodeConverter codeConverter;
    private final DTOConverter dtoConverter;

    // 설문 조회
    @Transactional(readOnly = true)
    public Survey getSurveyEntityById(int id){
        return surveyRepository.findById(id)
                .orElseThrow(()-> new SurveyException(SurveyErrorCode.SURVEY_NOT_FOUND));
    }

    // 새로운 설문 추가
    @Transactional
    public int addNewSurvey(CreateSurveyRequest createSurveyRequest){

        SurveyInfoDTO surveyInfoDTO = createSurveyRequest.surveyInfo();
        FilterDTO filters = createSurveyRequest.filters();

        User owner = userService.findByNickName(surveyInfoDTO.nickName());

        Survey survey = Survey.builder()
                .owner(owner)
                .title(surveyInfoDTO.title())
                .description(surveyInfoDTO.description())
                .imgUrl(surveyInfoDTO.imgUrl())
                .duration(surveyInfoDTO.duration())
                .maxHeadCnt(surveyInfoDTO.maxHeadCnt())
                .currentHeadCnt(0)
                .regionCode(codeConverter.convertRegionListToRegionCode(filters.regionList()))
                .jobCode(codeConverter.convertJobListToJobCode(filters.jobList()))
                .ageCode(codeConverter.convertAgeListToAgeCode(filters.ageList()))
                .genderCode(codeConverter.convertGenderListToGenderCode(filters.genderList()))
                .point(100) // 포인트는 우리가 알아서 넣어줘야함
                .questionList(getQuestionList(createSurveyRequest.questions()))
                .build();

        surveyRepository.save(survey);

        // 연관관계 편의메서드
        survey.getOwner().addRequestedSurvey(survey);

        return survey.getId();
    }

    // 설문 엔티티를 SurveyInfoResponse 로 반환
    @Transactional(readOnly = true)
    public SurveyResponse getSurveyResponseById(int id){
        Survey survey = getSurveyEntityById(id);
        return dtoConverter.toGetSurveyResponse(survey);
    }

    // 설문에 참여했을때 해당 설문의 currentHeadCnt를 1 증가시키는 함수
    @Transactional
    public void incrementHeadCnt(int sid){
        surveyRepository.incrementCurrentHeadCnt(sid);
    }

    // 특정 유저가 참여가능한 설문 조사
    @Transactional(readOnly = true)
    public List<SurveyResponse> getAvailableSurveys(String nickName) {
        User participant = userService.findByNickName(nickName);

        int participantRegionCode = participant.getRegionCode();
        int participantJobCode = participant.getJobCode();
        int participantAgeCode = participant.getAgeCode();
        int participantGenderCode = participant.getGenderCode();

        return surveyRepository.findAvailableSurveyByParticipant(participantRegionCode, participantJobCode, participantAgeCode, participantGenderCode)
                .stream()
                .map(dtoConverter::toGetSurveyResponse)
                .toList();
    }

    // Survey 에 집어넣을 QuestionList return
    private List<Question> getQuestionList(List<QuestionDTO> questionDTOList){
        List<Question> questionList = new ArrayList<>();
        for(QuestionDTO questionDTO : questionDTOList){
            Question curQuestion = Question.builder()
                    .title(questionDTO.title())
                    .isMultipleAnswer(questionDTO.isMultipleAnswer())
                    .type(questionDTO.questionType())
                    .build();

            curQuestion.getOptionList().addAll(getOptionList(questionDTO.optionList()));

            questionList.add(curQuestion);
        }
        return questionList;
    }

    // Question 에 집어넣을 OptionList return
    private List<Option> getOptionList(List<OptionDTO> optionDTOList){
        List<Option> optionList = new ArrayList<>();
        for (OptionDTO curOptionDTO : optionDTOList) {
            Option option = Option.builder()
                    .text(curOptionDTO.text())
                    .build();
            optionList.add(option);
        }
        return optionList;
    }
}