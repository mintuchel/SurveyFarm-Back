package notblank.boatvote.survey.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.survey.dto.request.SurveyDTO;
import notblank.boatvote.domain.survey.dto.response.SurveyInfoResponse;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.survey.repository.SurveyRepository;
import notblank.boatvote.domain.survey.service.SurveyService;
import notblank.boatvote.domain.survey.utility.CodeConverter;
import notblank.boatvote.domain.user.entity.User;
import notblank.boatvote.domain.user.repository.UserRepository;
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

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {
    @InjectMocks
    private SurveyService surveyService;

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private UserRepository userRepository;

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
                .username("modric")
                .password("1234")
                .regionCode(2) // 서울
                .jobCode(64) // 개발자
                .ageCode(40) // 대학생, 20대
                .genderCode(1) // 남자
                .build();
    }

    private void participantSetUp(){
        participant = User.builder()
                .username("mintuchel")
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
                .createdAt(LocalDateTime.now())
                .endAt(LocalDateTime.now())
                .regionCode(14) // 서울(2), 경기(4), 인천(8)
                .jobCode(112) // 개발(64) + 마케팅(32) + 회계(16)
                .ageCode(48) // 20대(32) + 10대(16)
                .genderCode(1) // 남자(1)
                .headCnt(100)
                .point(10)
                .build();

        survey.getQuestionList().add(question);
    }

    @BeforeEach
    public void testSetUp(){
        codeConverter.initCodeConverter();

        optionSetUp();
        questionSetUp();
        surveySetUp();

        ownerSetUp();
        participantSetUp();
    }

    @Test
    @DisplayName("설문 조회 성공 (entity to responseDTO 성공)")
    public void findSurveySuccess(){
        // given
        given(surveyRepository.findById(123)).willReturn(Optional.of(survey));

        // when
        SurveyInfoResponse response = surveyService.getSurveyById(123);

        // then
        Assertions.assertThat(response.headCnt()).isEqualTo(100);
        Assertions.assertThat(response.questionList()).hasSize(1);
        Assertions.assertThat(response.questionList().get(0).title()).isEqualTo(question.getTitle());
        Assertions.assertThat(response.selectedRegion()).contains("서울","경기","인천");
        Assertions.assertThat(response.selectedJob()).contains("개발","마케팅","회계");
        Assertions.assertThat(response.selectedAge()).contains("10대","20대");
        Assertions.assertThat(response.selectedGender()).contains("남자");
        Assertions.assertThat(response.questionList().get(0).optionList().get(0).text()).isEqualTo("chelsea");
        Assertions.assertThat(response.questionList().get(0).optionList().get(1).text()).isEqualTo("arsenal");
    }

    @Test
    @DisplayName("의뢰된 설문 저장 성공 (requestDTO to entity 성공)")
    public void addSurveySuccess() throws JsonProcessingException{
        // given
        given(userRepository.findById(123)).willReturn(Optional.of(owner));

        ArgumentCaptor<Survey> argumentCaptor = ArgumentCaptor.forClass(Survey.class);

        // when
        surveyService.addNewSurvey(surveyDTO());

        // then
        verify(surveyRepository).save(argumentCaptor.capture());
        Survey savedSurvey = argumentCaptor.getValue();

        Assertions.assertThat(savedSurvey.getDescription()).isEqualTo("this is survey description");
        Assertions.assertThat(savedSurvey.getQuestionList()).hasSize(3);
        Assertions.assertThat(savedSurvey.getQuestionList().get(0).getOptionList()).hasSize(4);
        Assertions.assertThat(savedSurvey.getRegionCode()).isEqualTo(14); // 서울 + 경기 + 인천
        Assertions.assertThat(savedSurvey.getJobCode()).isEqualTo(112); // 개발 + 마케팅 + 회계
        Assertions.assertThat(savedSurvey.getAgeCode()).isEqualTo(48); // 10대 + 20대
        Assertions.assertThat(savedSurvey.getGenderCode()).isEqualTo(1); // 남자
        Assertions.assertThat(owner.getRequestedSurveyList()).hasSize(1);
    }

    @Test
    @DisplayName("참여 가능한 설문 조회 성공")
    public void getAvailableSurveySuccess() throws JsonProcessingException{
        // given
        given(userRepository.findById(100)).willReturn(Optional.of(participant));
        given(userRepository.findById(123)).willReturn(Optional.of(owner));
        // given(surveyRepository.findAvailableSurveyByParticipant(2,64,40,1)).willReturn(Optional.of(survey));

        // when
        surveyService.addNewSurvey(surveyDTO());
        List<SurveyInfoResponse> list = surveyService.getAvailableSurveys(100);

        // then
        Assertions.assertThat(list.get(0).ownerId()).isEqualTo(123);
        Assertions.assertThat(list.get(0).selectedJob().contains("개발"));
        Assertions.assertThat(list.get(0).selectedGender().contains("남자"));
        Assertions.assertThat(list).hasSize(1);
    }

    private SurveyDTO surveyDTO() throws JsonProcessingException {
        String jsonString = "{\n"
                + "  \"ownerId\": 123,\n"
                + "  \"selectedRegion\": [\"서울\", \"경기\", \"인천\"],\n"
                + "  \"selectedJob\": [\"개발\", \"마케팅\", \"회계\"],\n"
                + "  \"selectedGender\": [\"남자\"],\n"
                + "  \"selectedAge\": [\"20대\", \"10대\"],\n"
                + "  \"selectedHeadCnt\": \"1000\",\n"
                + "  \"selectedDuration\": \"5\",\n"
                + "  \"description\": \"this is survey description\",\n"
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
        return objectMapper.readValue(jsonString, SurveyDTO.class);
    }
}
