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
import notblank.boatvote.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;
    private final CodeConverter codeConverter;

    // 새로운 설문 추가
    @Transactional
    public int addNewSurvey(NewSurveyRequest newSurveyRequest){
        int ownerId = newSurveyRequest.ownerId();
        User owner = userRepository.findById(ownerId).orElseThrow();

        Survey survey = Survey.builder()
                .owner(owner)
                .regionCode(codeConverter.convertRegionListToRegionCode(newSurveyRequest.selectedRegion()))
                .jobCode(codeConverter.convertJobListToJobCode(newSurveyRequest.selectedJob()))
                .ageCode(codeConverter.convertAgeListToAgeCode(newSurveyRequest.selectedAge()))
                .genderCode(codeConverter.convertGenderListToGenderCode(newSurveyRequest.selectedGender()))
                .headCnt(newSurveyRequest.selectedHeadCnt())
                .description(newSurveyRequest.description())
                .questionList(getQuestionList(newSurveyRequest.questionList()))
                .point(100) // 포인트는 우리가 알아서 넣어줘야함
                .build();

        surveyRepository.save(survey);
        survey.getOwner().addRequestedSurvey(survey); // 연관관계 편의메서드

        // survey.setEndAt(newSurveyRequest.duration());
        return survey.getId();
    }

    // 설문 단건 조회
    // 예외 커스텀 해줘야함
    @Transactional(readOnly = true)
    public SurveyInfoResponse getSurveyById(int id){
        Survey survey = surveyRepository.findById(id).orElseThrow();
        return changeSurveyToResponseDTO(survey);
    }

    // 특정 유저가 참여가능한 설문 조사
    @Transactional(readOnly = true)
    public List<SurveyInfoResponse> getAvailableSurveys(int participantId) {
        User participant = userRepository.findById(participantId).orElseThrow();

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

    // 설문을 ResponseDTO 로
    private SurveyInfoResponse changeSurveyToResponseDTO(Survey survey){
        return new SurveyInfoResponse(
                survey.getId(),
                survey.getOwner().getId(),
                codeConverter.convertRegionCodeToList(survey.getRegionCode()),
                codeConverter.convertJobCodeToList(survey.getJobCode()),
                codeConverter.convertGenderCodeToList(survey.getGenderCode()),
                codeConverter.convertAgeCodeToList(survey.getAgeCode()),
                survey.getHeadCnt(),
                survey.getPoint(),
                survey.getEndAt(),
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