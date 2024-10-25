package notblank.boatvote.survey.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.survey.dto.response.SurveyInfoResponse;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.survey.repository.SurveyRepository;
import notblank.boatvote.domain.survey.service.SurveyService;
import notblank.boatvote.domain.user.entity.User;

import notblank.boatvote.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
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
    public void getAvailableSurveyByRonaldoSuccess() throws JsonProcessingException{
        // given
        User participant = userRepository.findByUsername("ronaldo");

        // when
        List<SurveyInfoResponse> list = surveyService.getAvailableSurveys(participant.getId());

        // then
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0).questionList()).hasSize(1);
    }

    @Test
    @Rollback
    @DisplayName("음바페가 참여가능한 설문 조회 성공")
    public void getAvailableSurveyByMbappeSuccess() throws JsonProcessingException{
        // given
        User participant = userRepository.findByUsername("mbappe");

        // when
        List<SurveyInfoResponse> list = surveyService.getAvailableSurveys(participant.getId());

        // then
        Assertions.assertThat(list).hasSize(1);
        Assertions.assertThat(list.get(0).questionList()).hasSize(2);
    }
}
