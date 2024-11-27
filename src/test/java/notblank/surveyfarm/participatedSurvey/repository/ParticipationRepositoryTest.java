package notblank.surveyfarm.participatedSurvey.repository;

import notblank.surveyfarm.domain.participation.entity.Participation;
import notblank.surveyfarm.domain.participation.repository.ParticipationRepository;
import notblank.surveyfarm.domain.survey.entity.Survey;
import notblank.surveyfarm.domain.survey.repository.SurveyRepository;
import notblank.surveyfarm.domain.user.entity.User;
import notblank.surveyfarm.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ParticipationRepositoryTest {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    private User owner;
    private User participant;
    private Survey survey;

    @BeforeEach
    public void testSetUp(){
        owner = User.builder()
                .id(1)
                .nickName("pedroneto")
                .build();

        participant = User.builder()
                .id(2)
                .nickName("madueke")
                .build();

        survey = Survey.builder()
                .id(1)
                .owner(owner)
                .build();
    }

    @Test
    @DisplayName("참여내역 저장 성공(@GeneratedValue + @CreationTimeStamp 정상 작동 확인)")
    public void participateSurveySuccess(){
        // given
        Participation ps = Participation.builder()
                .user(participant)
                .survey(survey)
                .build();

        // when
        participationRepository.save(ps);

        // then
        // @GeneratedValue 정상 작동 확인1
        Assertions.assertThat(ps.getId()).isNotNull();

        // @CreationTimeStamp 정상 작동 확인
        LocalDateTime participatedAt = ps.getParticipatedAt(); // 변경 감지로 인한 재조회하지 않고 즉시 참조 가능
        Assertions.assertThat(participatedAt).isNotNull();

        System.out.println(ps.getId());
        System.out.println(ps.getSurvey().getId());
        System.out.println(ps.getUser().getId());
    }
}
