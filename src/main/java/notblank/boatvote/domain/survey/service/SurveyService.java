package notblank.boatvote.domain.survey.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.survey.dto.request.OptionDTO;
import notblank.boatvote.domain.survey.dto.request.QuestionDTO;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.survey.dto.request.SurveyDTO;
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

    @Transactional
    public int addNewSurvey(SurveyDTO surveyDTO){
        int ownerId = surveyDTO.ownerId();
        User owner = userRepository.findById(ownerId).orElseThrow();

        Survey survey = Survey.builder()
                .owner(owner)
                .headCnt(surveyDTO.headCnt())
                .regionCode(codeConverter.convertRegionListToRegionCode(surveyDTO.selectedRegion()))
                .jobCode(codeConverter.convertJobListToJobCode(surveyDTO.selectedJob()))
                .ageCode(codeConverter.convertAgeListToAgeCode(surveyDTO.selectedAge()))
                .questionList(getQuestionList(surveyDTO.questionList()))
                .point(100) // 포인트는 우리가 알아서 넣어줘야함
                .build();

        surveyRepository.save(survey);
        survey.getOwner().addRequestedSurvey(survey); // 연관관계 편의메서드

        // survey.setEndAt(surveyDTO.duration());
        return survey.getId();
    }

    private SurveyInfoResponse changeSurveyToResponseDTO(Survey survey){
        return new SurveyInfoResponse(
                survey.getId(),
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
                            return new QuestionDTO(
                                    question.getTitle(),
                                    question.getOptionList().stream()
                                            .map(option -> {
                                                return new OptionDTO(
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

    // 예외 커스텀 해줘야함
    @Transactional(readOnly = true)
    public SurveyInfoResponse getSurveyById(int id){
        Survey survey = surveyRepository.findById(id).orElseThrow();
        return changeSurveyToResponseDTO(survey);
    }

    @Transactional(readOnly = true)
    public List<SurveyInfoResponse> getAvailableSurveys(int participantId) {
        User participant = userRepository.findById(participantId).orElseThrow();

        int user_region_code = participant.getRegionCode();
        int user_job_code = participant.getJobCode();
        int user_age_code = participant.getAgeCode();

        return surveyRepository.findAvailableSurveyByParticipant(user_region_code, user_job_code, user_age_code)
                .stream()
                .map(this::changeSurveyToResponseDTO) // changeSurveyToResponseDTO 메서드를 사용
                .collect(Collectors.toList());
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
                    .cnt(0)
                    .build();
        }
        return optionList;
    }
}