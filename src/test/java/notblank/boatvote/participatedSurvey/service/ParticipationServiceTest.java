package notblank.boatvote.participatedSurvey.service;

import notblank.boatvote.domain.participation.vo.ParticipationInfoVO;
import notblank.boatvote.domain.participation.repository.ParticipationRepository;
import notblank.boatvote.domain.participation.service.ParticipationService;
import notblank.boatvote.domain.survey.dto.response.SurveyInfoResponse;
import notblank.boatvote.domain.survey.service.SurveyService;
import notblank.boatvote.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.assertj.core.api.Assertions;

import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ParticipationServiceTest {
    @InjectMocks
    private ParticipationService participationService;

    @Mock
    private SurveyService surveyService;
    @Mock
    private ParticipationRepository participationRepository;

    private User participant;
    private User owner;
    private ParticipationInfoVO participationInfoVO;
    private SurveyInfoResponse surveyInfoResponse;

    @BeforeEach
    public void testSetUp(){

        participationInfoVO = new ParticipationInfoVO(1111, LocalDateTime.now());

        owner = User.builder()
                .userName("modric")
                .password("1234")
                .regionCode(2) // 서울
                .jobCode(64) // 개발자
                .ageCode(40) // 대학생, 20대
                .genderCode(1) // 남자
                .build();

        participant = User.builder()
                .id(9999)
                .build();

       surveyInfoResponse = new SurveyInfoResponse(
                1111, // sid
                "modric", // ownerName
                "sample title", // title
                "sample image url", // imgUrl
                Arrays.asList("서울", "경기"), // selectedRegion
                Arrays.asList("개발자", "디자이너"), // selectedJob
                Arrays.asList("남자", "여자"), // selectedGender
                Arrays.asList("10대", "20대"), // selectedAge
                100, // maxHeadCnt
                25, // currentHeadCnt
                25.0, // progressRate
                350, // point
                LocalDateTime.now(), // createdAt
                LocalDateTime.now().plusDays(30), // endAt
                null, // participatedAt (여기서는 null로 설정)
                "This survey aims to gather opinions on...", // description
                null
        );
    }

    @Test
    public void getParticipatedSurveySuccess() {
        // given
        given(participationRepository.getUserParticipationInfo(9999)).willReturn(List.of(participationInfoVO));
        given(surveyService.getSurveyInfoResponseById(1111)).willReturn(surveyInfoResponse);

        // when
        List<SurveyInfoResponse> list = participationService.getParticipatedSurveyByUser(9999);

        // then
        Assertions.assertThat(list).hasSize(1);
    }
}
