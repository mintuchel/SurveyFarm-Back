package notblank.boatvote.survey.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.survey.dto.request.NewSurveyRequest;
import notblank.boatvote.domain.survey.dto.response.SurveyInfoResponse;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.survey.repository.SurveyRepository;
import notblank.boatvote.domain.survey.service.SurveyService;
import notblank.boatvote.domain.survey.utility.CodeConverter;
import notblank.boatvote.domain.user.entity.User;
import notblank.boatvote.domain.user.service.UserService;
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
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {
    @InjectMocks
    private SurveyService surveyService;

    @Mock
    private UserService userService;

    @Mock
    private SurveyRepository surveyRepository;

    @Spy
    private CodeConverter codeConverter;

    private User owner;
    private User participant;

    private Survey survey;
    private Question question;
    private Option option1;
    private Option option2;

    private void ownerSetUp(){
        owner = User.builder()
                .userName("modric")
                .password("1234")
                .regionCode(2) // 서울
                .jobCode(64) // 개발자
                .ageCode(40) // 대학생, 20대
                .genderCode(1) // 남자
                .build();
    }

    private void participantSetUp(){
        participant = User.builder()
                .userName("mintuchel")
                .password("1234")
                .regionCode(2) // 서울
                .jobCode(64) // 개발자
                .ageCode(40) // 대학생, 20대
                .genderCode(1) // 남자
                .build();
    }

    private void optionSetUp(){
        option1 = Option.builder()
                .text("chelsea")
                .build();

        option2 = Option.builder()
                .text("arsenal")
                .build();
    }

    private void questionSetUp(){
        question = Question.builder()
                .title("응원하는 팀 고르셈")
                .type(QuestionType.MC)
                .isMultipleAnswer(false)
                .build();

        question.getOptionList().add(option1);
        question.getOptionList().add(option2);
    }

    private void surveySetUp(){
        survey = Survey.builder()
                .id(123)
                .owner(owner)
                .title("sample title")
                .imgUrl("sample imgUrl")
                .regionCode(7) // 서울(1), 경기(2), 인천(4)
                .jobCode(9) // 기획·전략(1), 회계·세무(8)
                .ageCode(3) // 10대(1) + 20대(2)
                .genderCode(1) // 남자(1)
                .maxHeadCnt(100)
                .currentHeadCnt(0)
                .createdAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().plusDays(30))
                .point(350)
                .duration(5)
                .build();

        survey.getQuestionList().add(question);
    }

    @BeforeEach
    public void testSetUp(){
        codeConverter.initCodeConverter();

        ownerSetUp();
        participantSetUp();

        optionSetUp();
        questionSetUp();
        surveySetUp();
    }

    @Test
    @DisplayName("설문 조회 성공 (entity to responseDTO 성공)")
    public void getSurveyInfoResponseSuccess(){
        // given
        given(surveyRepository.findById(123)).willReturn(Optional.of(survey));

        // when
        SurveyInfoResponse response = surveyService.getSurveyInfoResponseById(123);

        // then
        Assertions.assertThat(response.maxHeadCnt()).isEqualTo(100);
        Assertions.assertThat(response.questionList()).hasSize(1);
        Assertions.assertThat(response.questionList().get(0).title()).isEqualTo(question.getTitle());
        Assertions.assertThat(response.selectedRegion()).contains("서울","경기","인천");
        Assertions.assertThat(response.selectedJob()).contains("기획·전략","회계·세무");
        Assertions.assertThat(response.selectedAge()).contains("10대","20대");
        Assertions.assertThat(response.selectedGender()).contains("남자");
        Assertions.assertThat(response.questionList().get(0).optionList().get(0).text()).isEqualTo("chelsea");
        Assertions.assertThat(response.questionList().get(0).optionList().get(1).text()).isEqualTo("arsenal");
    }

    @Test
    @DisplayName("의뢰된 설문 저장 성공 (requestDTO to entity 성공)")
    public void addSurveySuccess() throws JsonProcessingException{
        // given
        given(userService.findById(123)).willReturn(owner);

        ArgumentCaptor<Survey> argumentCaptor = ArgumentCaptor.forClass(Survey.class);

        // when
        surveyService.addNewSurvey(surveyDTO());

        // then
        verify(surveyRepository).save(argumentCaptor.capture());
        Survey savedSurvey = argumentCaptor.getValue();

        Assertions.assertThat(savedSurvey.getDescription()).isEqualTo("this is sample description");
        Assertions.assertThat(savedSurvey.getQuestionList()).hasSize(3);
        Assertions.assertThat(savedSurvey.getQuestionList().get(0).getOptionList()).hasSize(4);
        Assertions.assertThat(savedSurvey.getRegionCode()).isEqualTo(7); // 서울 + 경기 + 인천
        Assertions.assertThat(savedSurvey.getJobCode()).isEqualTo(9); // 기획·전략(1), 회계·세무(8)
        Assertions.assertThat(savedSurvey.getAgeCode()).isEqualTo(3); // 10대 + 20대
        Assertions.assertThat(savedSurvey.getGenderCode()).isEqualTo(1); // 남자
        Assertions.assertThat(owner.getRequestedSurveyList()).hasSize(1);
    }

    private NewSurveyRequest surveyDTO() throws JsonProcessingException {
        String jsonString = "{\n"
                + "  \"ownerId\": 123,\n"
                + "  \"title\": \"Sample Survey Title\",\n"
                + "  \"imgUrl\": \"sampleImageUrl\",\n"
                + "  \"regionList\": [\"서울\", \"경기\", \"인천\"],\n"
                + "  \"jobList\": [\"기획·전략\", \"회계·세무\"],\n"
                + "  \"genderList\": [\"남자\"],\n"
                + "  \"ageList\": [\"20대\", \"10대\"],\n"
                + "  \"maxHeadCnt\": \"1000\",\n"
                + "  \"duration\": \"5\",\n"
                + "  \"description\": \"this is sample description\",\n"
                + "  \"questionList\": [\n"
                + "    {\n"
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
                + "      \"title\": \"최근 5경기 니콜라스 잭슨의 폼에 대해 너의 의견을 적어줘\",\n"
                + "      \"optionList\": [],\n"
                + "      \"isMultipleAnswer\": false,\n"
                + "      \"questionType\": \"SA\"\n"
                + "    },\n"
                + "    {\n"
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
        return objectMapper.readValue(jsonString, NewSurveyRequest.class);
    }
}
