package notblank.boatvote.participatedSurvey.service;

import notblank.boatvote.domain.participatedSurvey.dto.request.ParticipateRequest;
import notblank.boatvote.domain.participatedSurvey.dto.response.ParticipatedSurveyDTO;
import notblank.boatvote.domain.participatedSurvey.entity.ParticipatedSurvey;
import notblank.boatvote.domain.participatedSurvey.repository.ParticipatedSurveyRepository;
import notblank.boatvote.domain.participatedSurvey.service.ParticipatedSurveyService;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.survey.service.SurveyService;
import notblank.boatvote.domain.user.entity.User;
import notblank.boatvote.domain.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ParticipatedSurveyServiceTest {
    @InjectMocks
    private ParticipatedSurveyService participatedSurveyService;

    @Mock
    private UserService userService;
    @Mock
    private SurveyService surveyService;
    @Mock
    private ParticipatedSurveyRepository participatedSurveyRepository;

    private User participant;
    private Survey survey;

    @BeforeEach
    public void testSetUp(){
        participant = User.builder()
                .id(9999)
                .build();

        survey = Survey.builder()
                .id(1111)
                .build();
    }

    @Test
    @DisplayName("참여내역 저장 성공(participatedAt 자동생성 성공)")
    public void participateSurveySuccess(){
        // given
        given(userService.findUserById(9999)).willReturn(participant);
        given(surveyService.findSurveyByUID(1111)).willReturn(survey);

        // when
        ParticipateRequest request = new ParticipateRequest(9999,1111);
        LocalDateTime participatedAt = participatedSurveyService.participateSurvey(request);

        // then
        Assertions.assertThat(participatedAt).isNotNull();
    }
}
