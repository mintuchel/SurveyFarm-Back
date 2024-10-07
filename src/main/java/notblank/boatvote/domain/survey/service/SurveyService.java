package notblank.boatvote.domain.survey.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.survey.dto.request.OptionDTO;
import notblank.boatvote.domain.survey.dto.request.QuestionDTO;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.survey.dto.request.SurveyDTO;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.survey.repository.SurveyRepository;
import notblank.boatvote.domain.user.entity.User;
import notblank.boatvote.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;

    @Transactional
    public int addNewSurvey(SurveyDTO surveyDTO){
//        int ownerId = surveyDTO.ownerId();
//        User owner = userRepository.findById(ownerId).orElseThrow();

        User owner = userRepository.findByUsername("mintuchel");

        Survey survey = Survey.builder()
                .owner(owner)
                .headCnt(surveyDTO.headCnt())
                .point(100) // 포인트는 우리가 알아서 넣어줘야함
                .build();

        // 의뢰받은 QuestionDTO들 빼내기
        List<QuestionDTO> questionDTOList = surveyDTO.questionList();

        for(QuestionDTO questionDTO : questionDTOList){

            Question curQuestion = Question.builder()
                    .title(questionDTO.title())
                    .isMultipleAnswer(questionDTO.isMultipleAnswer())
                    .type(questionDTO.questionType())
                    .build();

            if(questionDTO.questionType() == QuestionType.MC) {
                List<OptionDTO> optionList = questionDTO.optionList();
                for (OptionDTO curOptionDTO: optionList) {
                    Option option = Option.builder()
                            .text(curOptionDTO.text())
                            .cnt(0)
                            .build();
                    curQuestion.getOptionList().add(option);
                }
            }

            survey.getQuestionList().add(curQuestion);
        }

        surveyRepository.save(survey);
        survey.getOwner().addRequestedSurvey(survey); // 연관관계 편의메서드

        survey.setEndAt(surveyDTO.duration());
        return survey.getId();
    }

    // 나중에 예외처리해야함
    @Transactional(readOnly = true)
    public SurveyDTO getSurveyById(int surveyId){
        return surveyRepository.findById(surveyId)
                .map(survey -> {
                    SurveyDTO dto = new SurveyDTO(

                    )
                }
        );
    }
    
    // 이거 메서드 분리해보기
    private void setOptionsToQuestion(Question question){

    }
}