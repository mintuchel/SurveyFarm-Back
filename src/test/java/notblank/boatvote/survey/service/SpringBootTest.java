package notblank.boatvote.survey.service;

import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.survey.dto.response.QuestionInfoResponse;
import notblank.boatvote.domain.survey.dto.response.SurveyInfoResponse;
import notblank.boatvote.domain.survey.repository.SurveyRepository;
import notblank.boatvote.domain.survey.service.SurveyService;
import notblank.boatvote.domain.user.entity.User;

import notblank.boatvote.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@org.springframework.boot.test.context.SpringBootTest
public class SpringBootTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private SurveyService surveyService;

    @Test
    @Rollback
    @DisplayName("호날두가 참여가능한 설문 조회 성공")
    public void getAvailableSurveyByRonaldoSuccess() {
        // given
        User participant = userRepository.findByUserName("ronaldo");

        // when
        List<SurveyInfoResponse> list = surveyService.getAvailableSurveys(participant.getId());

        // then
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0).sid()).isNotNull();
        Assertions.assertThat(list.get(0).questionList()).hasSize(5);
        QuestionInfoResponse dto = list.get(0).questionList().get(0);
        Assertions.assertThat(dto.qid()).isNotNull();
        Assertions.assertThat(dto.questionType()).isEqualTo(QuestionType.MC);
        Assertions.assertThat(dto.title()).isEqualTo("가장 선호하는 운동 종류는 무엇인가요?");
    }

    @Test
    @Rollback
    @DisplayName("비니시우스가 참여가능한 설문 조회 0개 성공")
    public void getAvailableSurveyByViniSuccess() {
        // given
        User participant = userRepository.findByUserName("vini");

        // when
        List<SurveyInfoResponse> list = surveyService.getAvailableSurveys(participant.getId());

        // then
        Assertions.assertThat(list).hasSize(2);
    }

    @Test
    @Rollback
    @DisplayName("음바페가 참여가능한 설문 조회 성공")
    public void getAvailableSurveyByMbappeSuccess() {
        // given
        User participant = userRepository.findByUserName("mbappe");

        // when
        List<SurveyInfoResponse> list = surveyService.getAvailableSurveys(participant.getId());

        // then
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0).questionList()).hasSize(5);
    }
}
