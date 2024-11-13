package notblank.surveyfarm.survey.service;

import notblank.surveyfarm.domain.question.entity.QuestionType;
import notblank.surveyfarm.domain.survey.dto.internal.QuestionDTO;
import notblank.surveyfarm.domain.survey.dto.response.SurveyResponse;
import notblank.surveyfarm.domain.survey.service.SurveyService;
import notblank.surveyfarm.domain.user.entity.User;

import notblank.surveyfarm.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
public class SurveyBootTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SurveyService surveyService;

    @Test
    @Rollback
    @DisplayName("호날두가 참여가능한 설문 조회 성공")
    public void getAvailableSurveyByRonaldoSuccess() {
        // given
        User participant = userRepository.findByNickName("christiano ronaldo").orElseThrow();

        // when
        List<SurveyResponse> list = surveyService.getAvailableSurveys(participant.getId());

        // then
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0).surveyInfo().sid()).isNotNull();
        Assertions.assertThat(list.get(0).questions()).hasSize(5);
        QuestionDTO dto = list.get(0).questions().get(0);
        Assertions.assertThat(dto.qid()).isNotNull();
        Assertions.assertThat(dto.questionType()).isEqualTo(QuestionType.MC);
        Assertions.assertThat(dto.title()).isEqualTo("가장 선호하는 운동 종류는 무엇인가요?");
    }

    @Test
    @Rollback
    @DisplayName("비니시우스가 참여가능한 설문 조회 0개 성공")
    public void getAvailableSurveyByViniSuccess() {
        // given
        User participant = userRepository.findByNickName("vinicius jr").orElseThrow();

        // when
        List<SurveyResponse> list = surveyService.getAvailableSurveys(participant.getId());

        // then
        Assertions.assertThat(list).hasSize(2);
    }

    @Test
    @Rollback
    @DisplayName("음바페가 참여가능한 설문 조회 성공")
    public void getAvailableSurveyByMbappeSuccess() {
        // given
        User participant = userRepository.findByNickName("kylian mbappe").orElseThrow();

        // when
        List<SurveyResponse> list = surveyService.getAvailableSurveys(participant.getId());

        // then
        Assertions.assertThat(list).hasSize(2);
        Assertions.assertThat(list.get(0).questions()).hasSize(5);
    }
}
