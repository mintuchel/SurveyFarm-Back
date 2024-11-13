package notblank.surveyfarm.participatedSurvey.service;

import notblank.surveyfarm.domain.participation.vo.ParticipationInfoVO;
import notblank.surveyfarm.domain.participation.repository.ParticipationRepository;
import notblank.surveyfarm.domain.participation.service.ParticipationService;
import notblank.surveyfarm.domain.question.entity.QuestionType;
import notblank.surveyfarm.domain.survey.dto.internal.FilterDTO;
import notblank.surveyfarm.domain.survey.dto.internal.OptionDTO;
import notblank.surveyfarm.domain.survey.dto.internal.QuestionDTO;
import notblank.surveyfarm.domain.survey.dto.internal.SurveyInfoDTO;
import notblank.surveyfarm.domain.survey.dto.response.SurveyResponse;
import notblank.surveyfarm.domain.survey.service.SurveyService;
import notblank.surveyfarm.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    private int SID = 1111;
    private int UID = 9999;

    private User participant;
    private User owner;
    private ParticipationInfoVO participationInfoVO;
    private SurveyResponse surveyResponse;

    @BeforeEach
    public void testSetUp(){

        participationInfoVO = new ParticipationInfoVO(SID, LocalDateTime.now());

        owner = User.builder()
                .nickName("modric")
                .regionCode(2) // 서울
                .jobCode(64) // 개발자
                .ageCode(40) // 대학생, 20대
                .genderCode(1) // 남자
                .build();

        participant = User.builder()
                .id(UID)
                .nickName("valverde")
                .build();

       surveyResponse = GetSurveyResponse();
    }

    @Test
    @DisplayName("참여 설문 정보 반환 성공")
    public void getParticipatedSurveySuccess() {
        // given
        given(participationRepository.getUserParticipationInfo(UID)).willReturn(List.of(participationInfoVO));
        given(surveyService.getSurveyResponseById(SID)).willReturn(surveyResponse);

        // when
        List<SurveyResponse> list = participationService.getParticipatedSurveyByUser(UID);

        // then
        Assertions.assertThat(list).hasSize(1);
        System.out.println(list.get(0).participatedAt());
    }

    private SurveyResponse GetSurveyResponse() {
        // SurveyInfoDTO 생성
        SurveyInfoDTO surveyInfo = SurveyInfoDTO.builder()
                .sid(SID)
                .nickName("modric")
                .title("sample title")
                .description("This survey aims to gather opinions on...")
                .imgUrl("sample image url")
                .duration(30)
                .maxHeadCnt(100)
                .currentHeadCnt(25)
                .point(350)
                .createdAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().plusDays(30))
                .build();

        // FilterDTO 생성
        FilterDTO filters = FilterDTO.builder()
                .regionList(Arrays.asList("서울", "경기"))
                .jobList(Arrays.asList("개발자", "디자이너"))
                .genderList(Arrays.asList("남자", "여자"))
                .ageList(Arrays.asList("10대", "20대"))
                .build();

        // QuestionDTO 리스트 생성
        List<QuestionDTO> questions = List.of(
                QuestionDTO.builder()
                        .qid(1)
                        .title("최애 축구 선수는 누구인가요?")
                        .optionList(List.of(
                                new OptionDTO("손흥민"),
                                new OptionDTO("이강인")
                        ))
                        .isMultipleAnswer(false)
                        .questionType(QuestionType.MC)
                        .build(),
                QuestionDTO.builder()
                        .qid(2)
                        .title("최근 본 영화에 대해 간단히 말해주세요.")
                        .optionList(List.of())
                        .isMultipleAnswer(false)
                        .questionType(QuestionType.SA)
                        .build(),
                QuestionDTO.builder()
                        .qid(3)
                        .title("가고 싶은 여행지를 모두 선택해주세요.")
                        .optionList(List.of(
                                new OptionDTO("프랑스"),
                                new OptionDTO("스페인"),
                                new OptionDTO("이탈리아")
                        ))
                        .isMultipleAnswer(true)
                        .questionType(QuestionType.MC)
                        .build()
        );

        // GetSurveyResponse 반환
        return SurveyResponse.builder()
                .participatedAt(LocalDateTime.now().minusDays(10))  // 참여 시간이 없는 경우 null
                .surveyInfo(surveyInfo)
                .filters(filters)
                .questions(questions)
                .build();
    }
}
