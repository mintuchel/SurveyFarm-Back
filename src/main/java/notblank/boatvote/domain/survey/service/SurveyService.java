package notblank.boatvote.domain.survey.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.survey.dto.request.NewSurveyRequest;
import notblank.boatvote.domain.survey.dto.request.NewOptionRequest;
import notblank.boatvote.domain.survey.dto.request.NewQuestionRequest;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.survey.dto.response.SurveyInfoResponse;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.survey.repository.SurveyRepository;
import notblank.boatvote.domain.survey.utility.CodeConverter;
import notblank.boatvote.domain.user.entity.User;
import notblank.boatvote.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final UserService userService;

    private final SurveyRepository surveyRepository;

    private final CodeConverter codeConverter;

    // 새로운 설문 추가
    @Transactional
    public int addNewSurvey(NewSurveyRequest newSurveyRequest){

        User owner = userService.findById(newSurveyRequest.ownerId());

        Survey survey = Survey.builder()
                .owner(owner)
                .title(newSurveyRequest.title())
                .description(newSurveyRequest.description())
                .imgUrl(newSurveyRequest.imgUrl())
                .duration(newSurveyRequest.duration())
                .maxHeadCnt(newSurveyRequest.maxHeadCnt())
                .currentHeadCnt(0)
                .regionCode(codeConverter.convertRegionListToRegionCode(newSurveyRequest.regionList()))
                .jobCode(codeConverter.convertJobListToJobCode(newSurveyRequest.jobList()))
                .ageCode(codeConverter.convertAgeListToAgeCode(newSurveyRequest.ageList()))
                .genderCode(codeConverter.convertGenderListToGenderCode(newSurveyRequest.genderList()))
                .point(100) // 포인트는 우리가 알아서 넣어줘야함
                .questionList(getQuestionList(newSurveyRequest.questionList()))
                .build();

        surveyRepository.save(survey);

        // 연관관계 편의메서드
        survey.getOwner().addRequestedSurvey(survey);

        return survey.getId();
    }

    // SurveyInfoResponse 를 return
    @Transactional(readOnly = true)
    public SurveyInfoResponse getSurveyInfoResponseById(int id){
        Survey survey = surveyRepository.findById(id).orElseThrow();
        return SurveyInfoResponse.toResponse(
                survey,
                codeConverter.convertRegionCodeToList(survey.getRegionCode()),
                codeConverter.convertJobCodeToList(survey.getJobCode()),
                codeConverter.convertAgeCodeToList(survey.getAgeCode()),
                codeConverter.convertGenderCodeToList(survey.getGenderCode())
                );
    }

    // Survey Entity 를 return
    @Transactional(readOnly = true)
    public Survey getSurveyEntityById(int id){
        return surveyRepository.findById(id).orElseThrow();
    }

    // 설문에 참여했을때 해당 설문의 currentHeadCnt를 1 증가시키는 함수
    @Transactional
    public void incrementHeadCnt(int sid){
        surveyRepository.incrementCurrentHeadCnt(sid);
    }

    // 특정 유저가 참여가능한 설문 조사
    @Transactional(readOnly = true)
    public List<SurveyInfoResponse> getAvailableSurveys(int participantId) {
        User participant = userService.findById(participantId);

        int participantRegionCode = participant.getRegionCode();
        int participantJobCode = participant.getJobCode();
        int participantAgeCode = participant.getAgeCode();
        int participantGenderCode = participant.getGenderCode();

        return surveyRepository.findAvailableSurveyByParticipant(participantRegionCode, participantJobCode, participantAgeCode, participantGenderCode)
                .stream()
                .map(survey -> {
                    return SurveyInfoResponse.toResponse(
                            survey,
                            codeConverter.convertRegionCodeToList(survey.getRegionCode()),
                            codeConverter.convertJobCodeToList(survey.getJobCode()),
                            codeConverter.convertAgeCodeToList(survey.getAgeCode()),
                            codeConverter.convertGenderCodeToList(survey.getGenderCode())
                    );
                })
                .collect(Collectors.toList());
    }

    // Survey 에 집어넣을 QuestionList return
    private List<Question> getQuestionList(List<NewQuestionRequest> newQuestionRequestList){
        List<Question> questionList = new ArrayList<>();
        for(NewQuestionRequest newQuestionRequest : newQuestionRequestList){
            Question curQuestion = Question.builder()
                    .title(newQuestionRequest.title())
                    .isMultipleAnswer(newQuestionRequest.isMultipleAnswer())
                    .type(newQuestionRequest.questionType())
                    .build();

            curQuestion.getOptionList().addAll(getOptionList(newQuestionRequest.optionList()));

            questionList.add(curQuestion);
        }
        return questionList;
    }

    // Question 에 집어넣을 OptionList return
    private List<Option> getOptionList(List<NewOptionRequest> newOptionRequestList){
        List<Option> optionList = new ArrayList<>();
        for (NewOptionRequest curNewOptionRequest : newOptionRequestList) {
            Option option = Option.builder()
                    .text(curNewOptionRequest.text())
                    .build();
            optionList.add(option);
        }
        return optionList;
    }
}