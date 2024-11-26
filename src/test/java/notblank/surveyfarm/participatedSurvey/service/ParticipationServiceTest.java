package notblank.surveyfarm.participatedSurvey.service;

import net.datafaker.Faker;
import notblank.surveyfarm.domain.participation.dto.request.ParticipationRequest;
import notblank.surveyfarm.domain.participation.vo.ParticipationInfoVO;
import notblank.surveyfarm.domain.participation.repository.ParticipationRepository;
import notblank.surveyfarm.domain.participation.service.ParticipationService;
import notblank.surveyfarm.domain.survey.dto.request.internal.SurveyFilterDTO;
import notblank.surveyfarm.domain.survey.dto.request.internal.SurveyInfoDTO;
import notblank.surveyfarm.domain.survey.dto.response.SurveyInfoResponse;
import notblank.surveyfarm.domain.survey.entity.Survey;
import notblank.surveyfarm.domain.survey.entity.SurveyStatus;
import notblank.surveyfarm.domain.survey.service.SurveyService;
import notblank.surveyfarm.domain.user.entity.User;
import notblank.surveyfarm.domain.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ParticipationServiceTest {
    @InjectMocks
    private ParticipationService participationService;

    @Mock
    private UserService userService;
    @Mock
    private SurveyService surveyService;

    @Mock
    private ParticipationRepository participationRepository;

    @Mock
    private ParticipationRequest request;

    @Mock
    private User user;
    @Mock
    private Survey survey;

    private Faker faker = new Faker();
    private int sid = faker.random().nextInt(1,100);
    private int uid = faker.random().nextInt(1,100);

    @BeforeEach
    public void testSetUp(){

    }

    @Test
    @DisplayName("참여내역 추가 성공")
    public void addNewParticipationSuccess(){
        // given
        when(request.sid()).thenReturn(sid);
        when(request.uid()).thenReturn(uid);

        given(participationRepository.checkIfUserParticipated(uid, sid)).willReturn(0);
        given(userService.findById(uid)).willReturn(user);
        given(surveyService.getSurveyEntityById(sid)).willReturn(survey);

        // when
        String participatedTime = participationService.addNewParticipation(request);

        // then
        System.out.println(participatedTime);

        Assertions.assertThat(participatedTime).isNotNull();
    }

    @Test
    @DisplayName("")
    public void getParticipatedSurveysSuccess(){
        // given

        // when

        // then
    }

    @Test
    @DisplayName("")
    public void getParticipatedTime(){

    }
}
