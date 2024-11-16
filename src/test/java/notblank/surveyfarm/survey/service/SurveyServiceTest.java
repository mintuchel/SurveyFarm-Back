package notblank.surveyfarm.survey.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import notblank.surveyfarm.domain.question.entity.Option;
import notblank.surveyfarm.domain.question.entity.Question;
import notblank.surveyfarm.domain.question.entity.QuestionType;
import notblank.surveyfarm.domain.survey.dto.internal.FilterDTO;
import notblank.surveyfarm.domain.survey.dto.internal.QuestionDTO;
import notblank.surveyfarm.domain.survey.dto.internal.SurveyInfoDTO;
import notblank.surveyfarm.domain.survey.dto.request.CreateSurveyRequest;
import notblank.surveyfarm.domain.survey.dto.response.SurveyResponse;
import notblank.surveyfarm.domain.survey.entity.Survey;
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

    private final Faker faker = new Faker();

    @Mock
    private User owner;
    @Mock
    private Survey survey;
    @Mock
    private Question question;
    @Mock
    private Option option1;
    @Mock
    private Option option2;

    int SURVEY_ID = faker.number().randomDigitNotZero();
    int OWNER_ID = faker.number().randomDigitNotZero();

    private void ownerSetUp(){
        when(owner.getId()).thenReturn(OWNER_ID);
        when(owner.getNickName()).thenReturn(faker.name().firstName());
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
    }

    @BeforeEach
    public void testSetUp(){
        codeConverter.initCodeConverter();

        ownerSetUp();
        questionSetUp();
        surveySetUp();
    }

    @Test
    @DisplayName("설문 조회 성공 (entity to responseDTO 성공)")
    public void getSurveyInfoResponseSuccess(){
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
        Assertions.assertThat(surveyInfo.nickName()).isNotBlank();
        Assertions.assertThat(questions).hasSize(1);
    }

    @Test
    @DisplayName("의뢰된 설문 저장 성공 (requestDTO to entity 성공)")
    public void addSurveySuccess() throws JsonProcessingException{
        // given
        given(userService.findById(OWNER_ID)).willReturn(owner);

        ArgumentCaptor<Survey> argumentCaptor = ArgumentCaptor.forClass(Survey.class);

        // when
        surveyService.addNewSurvey(surveyDTO());

        // then
        verify(surveyRepository).save(argumentCaptor.capture());
        Survey savedSurvey = argumentCaptor.getValue();

        Assertions.assertThat(savedSurvey.getDescription()).isEqualTo("This is a sample description");
        Assertions.assertThat(savedSurvey.getQuestionList()).hasSize(3);
        Assertions.assertThat(savedSurvey.getQuestionList().get(0).getOptionList()).hasSize(4);
        Assertions.assertThat(savedSurvey.getRegionCode()).isEqualTo(7); // 서울 + 경기 + 인천
        Assertions.assertThat(savedSurvey.getJobCode()).isEqualTo(9); // 기획·전략(1), 회계·세무(8)
        Assertions.assertThat(savedSurvey.getAgeCode()).isEqualTo(3); // 10대 + 20대
        Assertions.assertThat(savedSurvey.getGenderCode()).isEqualTo(1); // 남자
        Assertions.assertThat(owner.getRequestedSurveyList()).hasSize(1);
    }

    private CreateSurveyRequest surveyDTO() throws JsonProcessingException {
        String jsonString = "{\n"
                + "  \"surveyInfo\": {\n"
                + "    \"sid\": 1,\n"
                + "    \"uid\": 15,\n"
                + "    \"nickName\": \"SampleOwner\",\n"
                + "    \"title\": \"Sample Survey Title\",\n"
                + "    \"description\": \"This is a sample description\",\n"
                + "    \"imgUrl\": \"sampleImageUrl\",\n"
                + "    \"duration\": 5,\n"
                + "    \"maxHeadCnt\": 1000\n" // 쉼표 제거
                + "  },\n"
                + "  \"filters\": {\n"
                + "    \"regionList\": [\"서울\", \"경기\", \"인천\"],\n"
                + "    \"jobList\": [\"기획·전략\", \"회계·세무\"],\n"
                + "    \"genderList\": [\"남자\"],\n"
                + "    \"ageList\": [\"10대\", \"20대\"]\n"
                + "  },\n"
                + "  \"questions\": [\n"
                + "    {\n"
                + "      \"qid\": 1,\n"
                + "      \"title\": \"최애 첼시 선수는?\",\n"
                + "      \"optionList\": [\n"
                + "        {\"text\": \"파머\"},\n"
                + "        {\"text\": \"마두에케\"},\n"
                + "        {\"text\": \"엔조\"},\n"
                + "        {\"text\": \"카이세도\"}\n"
                + "      ],\n"
                + "      \"isMultipleAnswer\": false,\n"
                + "      \"questionType\": \"MC\"\n"
                + "    },\n"
                + "    {\n"
                + "      \"qid\": 2,\n"
                + "      \"title\": \"최근 5경기 니콜라스 잭슨의 폼에 대해 너의 의견을 적어줘\",\n"
                + "      \"optionList\": [],\n"
                + "      \"isMultipleAnswer\": false,\n"
                + "      \"questionType\": \"SA\"\n"
                + "    },\n"
                + "    {\n"
                + "      \"qid\": 3,\n"
                + "      \"title\": \"첼시에 영입하면 좋을거 같은 선수를 모두 골라\",\n"
                + "      \"optionList\": [\n"
                + "        {\"text\": \"손흥민\"},\n"
                + "        {\"text\": \"박지성\"},\n"
                + "        {\"text\": \"차범근\"}\n"
                + "      ],\n"
                + "      \"isMultipleAnswer\": true,\n"
                + "      \"questionType\": \"MC\"\n"
                + "    }\n"
                + "  ]\n"
                + "}";

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, CreateSurveyRequest.class);
    }

}
