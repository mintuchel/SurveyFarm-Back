package notblank.surveyfarm.survey.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.datafaker.Faker;
import notblank.surveyfarm.SurveyFarmDataFactory;
import notblank.surveyfarm.domain.question.entity.Option;
import notblank.surveyfarm.domain.question.entity.Question;
import notblank.surveyfarm.domain.survey.dto.internal.FilterDTO;
import notblank.surveyfarm.domain.survey.dto.internal.QuestionDTO;
import notblank.surveyfarm.domain.survey.dto.internal.SurveyInfoDTO;
import notblank.surveyfarm.domain.survey.dto.response.SurveyResponse;
import notblank.surveyfarm.domain.survey.entity.Survey;
import notblank.surveyfarm.domain.survey.entity.SurveyStatus;
import notblank.surveyfarm.domain.survey.repository.SurveyRepository;
import notblank.surveyfarm.domain.survey.service.SurveyService;
import notblank.surveyfarm.domain.utility.CodeConverter;
import notblank.surveyfarm.domain.utility.DTOConverter;
import notblank.surveyfarm.domain.user.entity.User;
import notblank.surveyfarm.domain.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {
    @InjectMocks
    private SurveyService surveyService;

    @Mock
    private UserService userService;

    @Mock
    private SurveyRepository surveyRepository;

    // 모두 실제 로직을 수행하는 진짜 객체이므로 Spy 로 선언
    // DTOConverter는 CodeConverter가 필요하므로 CodeConverter까지 생성
    // Spy 선언할때 생성자로 바로 초기화
    @Spy
    private CodeConverter codeConverter = new CodeConverter();

    // CodeConverter와의 의존성 문제는 @RequiredArgs를 통해 생성자 주입으로 해결
    @Spy
    private DTOConverter dtoConverter = new DTOConverter(codeConverter);

    // owner 는 실제 객체로 테스트해야함
    private User owner;

    @Mock
    private Survey survey;
    @Mock
    private Question question;
    @Mock
    private Option option1, option2;

    private final Faker faker = new Faker();

    private final int SURVEY_ID = faker.number().randomDigitNotZero();
    private final int OWNER_ID = faker.number().randomDigitNotZero();

    private void ownerSetUp(){
        owner = User.builder()
                .id(OWNER_ID)
                .nickName(faker.name().firstName())
                .regionCode(faker.number().numberBetween(1,100))
                .jobCode(faker.number().numberBetween(1,100))
                .ageCode(faker.number().numberBetween(1,100))
                .genderCode(faker.number().numberBetween(1,2))
                .build();
    }

    private void questionSetUp(){
        when(question.getOptionList()).thenReturn(List.of(option1, option2));
    }

    private void surveySetUp(){
        when(survey.getId()).thenReturn(SURVEY_ID);
        when(survey.getOwner()).thenReturn(owner);
        when(survey.getTitle()).thenReturn("sample title");
        when(survey.getImgUrl()).thenReturn("sample imgUrl");
        when(survey.getRegionCode()).thenReturn(faker.number().numberBetween(1, 1000));
        when(survey.getJobCode()).thenReturn(faker.number().numberBetween(1, 1000));
        when(survey.getAgeCode()).thenReturn(faker.number().numberBetween(1, 100));
        when(survey.getGenderCode()).thenReturn(faker.number().numberBetween(1, 2));
        when(survey.getMaxHeadCnt()).thenReturn(faker.number().numberBetween(1, 100));
        when(survey.getCurrentHeadCnt()).thenReturn(faker.number().numberBetween(1, 100));
        when(survey.getCreatedAt()).thenReturn(LocalDateTime.now());
        when(survey.getEndAt()).thenReturn(LocalDateTime.now().plusDays(30));
        when(survey.getPoint()).thenReturn(faker.number().numberBetween(1, 100));
        when(survey.getDuration()).thenReturn(faker.number().numberBetween(1, 10));
        when(survey.getQuestionList()).thenReturn(List.of(question));
        when(survey.getSurveyStatus()).thenReturn(SurveyStatus.IN_PROGRESS);
    }

    @BeforeEach
    public void testSetUp(){
        codeConverter.initCodeConverter();

        ownerSetUp();
    }

    @Test
    @DisplayName("설문 조회 성공 (entity to responseDTO 성공)")
    public void getSurveyInfoResponseSuccess(){
        // setup
        questionSetUp();
        surveySetUp();

        // given
        given(surveyRepository.findById(SURVEY_ID)).willReturn(Optional.of(survey));

        // when
        SurveyResponse response = surveyService.getSurveyResponseById(SURVEY_ID);

        // then
        SurveyInfoDTO surveyInfo = response.surveyInfo();
        FilterDTO filters = response.filters();
        List<QuestionDTO> questions = response.questions();

        System.out.println(filters.regionList());
        System.out.println(filters.jobList());
        System.out.println(filters.ageList());

        Assertions.assertThat(surveyInfo.sid()).isEqualTo(SURVEY_ID);
        Assertions.assertThat(surveyInfo.surveyStatus()).isEqualTo(SurveyStatus.IN_PROGRESS);
        Assertions.assertThat(surveyInfo.nickName()).isNotBlank();
        Assertions.assertThat(questions).hasSize(1);
    }

    @Test
    @DisplayName("의뢰된 설문 저장 성공 (requestDTO to entity 성공)")
    public void addSurveySuccess() throws JsonProcessingException{
        // given
        given(userService.findById(any(Integer.class))).willReturn(owner);

        ArgumentCaptor<Survey> argumentCaptor = ArgumentCaptor.forClass(Survey.class);

        // when
        surveyService.addNewSurvey(SurveyFarmDataFactory.getCreateSurveyRequestDTO());

        // then
        verify(surveyRepository).save(argumentCaptor.capture());
        Survey savedSurvey = argumentCaptor.getValue();

        Assertions.assertThat(savedSurvey.getDescription()).isEqualTo("This is a sample description");
        Assertions.assertThat(savedSurvey.getQuestionList()).hasSize(3);
        Assertions.assertThat(savedSurvey.getQuestionList().get(0).getOptionList()).hasSize(4);
        Assertions.assertThat(savedSurvey.getRegionCode()).isEqualTo(codeConverter.convertRegionListToRegionCode(List.of("서울","경기","인천")));
        Assertions.assertThat(savedSurvey.getJobCode()).isEqualTo(codeConverter.convertJobListToJobCode(List.of("기획·전략","회계·세무")));
        Assertions.assertThat(savedSurvey.getAgeCode()).isEqualTo(codeConverter.convertAgeListToAgeCode(List.of("10대","20대")));
        Assertions.assertThat(savedSurvey.getGenderCode()).isEqualTo(codeConverter.convertGenderListToGenderCode(List.of("남자")));
        Assertions.assertThat(savedSurvey.getSurveyStatus()).isEqualTo(SurveyStatus.NEW);
        Assertions.assertThat(owner.getRequestedSurveyList()).hasSize(1);
    }
}
