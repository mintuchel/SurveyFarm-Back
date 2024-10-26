package notblank.boatvote.survey.repository;

import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.survey.repository.SurveyRepository;
import notblank.boatvote.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.assertj.core.api.Assertions;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class SurveyRepositoryTest {
    @Autowired
    private SurveyRepository surveyRepository;

    private User owner;
    private Survey survey;
    private Question q1;
    private Option opt1, opt2, opt3;

    private void OwnerSetUp(){

    }

    private void optionSetUp(){
        opt1 = Option.builder()
                .text("chelsea")
                .build();

        opt2 = Option.builder()
                .text("arsenal")
                .build();

        opt3 = Option.builder()
                .text("mancity")
                .build();
    }

    private void questionSetUp(){
        q1 = Question.builder()
                .title("응원하는 팀 고르셈")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();

        q1.getOptionList().add(opt1);
        q1.getOptionList().add(opt2);
        q1.getOptionList().add(opt3);
    }

    @BeforeEach
    public void surveySetUp() {
        optionSetUp();
        questionSetUp();

        survey = Survey.builder()
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59))
                .build();

        survey.getQuestionList().add(q1);
    }

    @Test
    @DisplayName("설문 저장 성공")
    public void saveSurveySuccess(){
        Survey savedSurvey = surveyRepository.save(survey);

        Assertions.assertThat(savedSurvey).isNotNull();
    }
}
