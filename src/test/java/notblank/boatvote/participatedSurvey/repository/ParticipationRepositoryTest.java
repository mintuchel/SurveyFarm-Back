package notblank.boatvote.participatedSurvey.repository;

import notblank.boatvote.domain.participation.entity.Participation;
import notblank.boatvote.domain.participation.repository.ParticipationRepository;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.survey.repository.SurveyRepository;
import notblank.boatvote.domain.user.entity.User;
import notblank.boatvote.domain.user.repository.UserRepository;
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
                .nickName("pedroneto")
                .build();
        participant = User.builder()
                .nickName("madueke")
                .build();

        userRepository.save(owner);
        userRepository.save(participant);

        survey = Survey.builder()
                .owner(owner)
                .build();

        surveyRepository.save(survey);
    }

    @Test
    @DisplayName("참여내역 저장 성공(participatedAt 자동생성 성공)")
    public void participateSurveySuccess(){
        // given
        Participation ps = Participation.builder()
                .user(participant)
                .survey(survey)
                .build();

        // when
        participationRepository.save(ps);
        LocalDateTime participatedAt = ps.getParticipatedAt();

        // then
        // @GeneratedValue 정상 작동 확인
        Assertions.assertThat(ps.getId()).isNotNull();
        // @CreationTimeStamp 정상 작동 확인
        Assertions.assertThat(participatedAt).isNotNull();
        System.out.println("participatedAt :" + participatedAt);
    }
}
