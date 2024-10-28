package notblank.boatvote.domain.survey.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.survey.dto.request.NewSurveyRequest;
import notblank.boatvote.domain.survey.dto.request.NewOptionRequest;
import notblank.boatvote.domain.survey.dto.request.NewQuestionRequest;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.survey.dto.response.OptionInfoResponse;
import notblank.boatvote.domain.survey.dto.response.QuestionInfoResponse;
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
                .imgUrl(newSurveyRequest.imgUrl())
                .regionCode(codeConverter.convertRegionListToRegionCode(newSurveyRequest.regionList()))
                .jobCode(codeConverter.convertJobListToJobCode(newSurveyRequest.jobList()))
                .ageCode(codeConverter.convertAgeListToAgeCode(newSurveyRequest.ageList()))
                .genderCode(codeConverter.convertGenderListToGenderCode(newSurveyRequest.genderList()))
                .maxHeadCnt(newSurveyRequest.maxHeadCnt())
                .currentHeadCnt(0)
                .description(newSurveyRequest.description())
                .questionList(getQuestionList(newSurveyRequest.questionList()))
                .point(100) // 포인트는 우리가 알아서 넣어줘야함
                .build();

        surveyRepository.save(survey);

        // 연관관계 편의메서드
        survey.getOwner().addRequestedSurvey(survey);
        // endAt duration으로 설정
        survey.setEndAt(newSurveyRequest.duration());

        return survey.getId();
    }

    // SurveyInfoResponse 를 return
    @Transactional(readOnly = true)
    public SurveyInfoResponse getSurveyInfoResponseById(int id){
        Survey survey = surveyRepository.findById(id).orElseThrow();
        return changeSurveyToResponseDTO(survey);
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
                .map(this::changeSurveyToResponseDTO) // changeSurveyToResponseDTO 메서드를 사용
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

    // Survey 엔티티를 SurveyInfoResponseDTO 로 바꿔주는 private 함수
    private SurveyInfoResponse changeSurveyToResponseDTO(Survey survey){

        return new SurveyInfoResponse(
                survey.getId(),
                survey.getOwner().getUserName(),
                survey.getTitle(),
                survey.getImgUrl(),
                codeConverter.convertRegionCodeToList(survey.getRegionCode()),
                codeConverter.convertJobCodeToList(survey.getJobCode()),
                codeConverter.convertGenderCodeToList(survey.getGenderCode()),
                codeConverter.convertAgeCodeToList(survey.getAgeCode()),
                survey.getMaxHeadCnt(),
                survey.getCurrentHeadCnt(),
                (double) survey.getMaxHeadCnt() / survey.getCurrentHeadCnt(),
                survey.getPoint(),
                survey.getCreatedAt(),
                survey.getEndAt(),
                null,
                survey.getDescription(),
                survey.getQuestionList().stream()
                        .map(question -> {
                            return new QuestionInfoResponse(
                                    question.getId(),
                                    question.getTitle(),
                                    question.getOptionList().stream()
                                            .map(option -> {
                                                return new OptionInfoResponse(
                                                        option.getText()
                                                );
                                            })
                                            .collect(Collectors.toList()),
                                    question.isMultipleAnswer(),
                                    question.getType()
                            );
                        })
                        .collect(Collectors.toList())
        );
    }
}