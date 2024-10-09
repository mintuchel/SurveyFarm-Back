package notblank.boatvote.survey.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Arrays;
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

    // codeConverter 실제 동작 확인 위해 spy 로 지정
    // mock 하면 내가 given 으로 행동 강령을 명시해야함
    @Spy
    private CodeConverter codeConverter;

    private User owner;
    private Survey survey;

    @BeforeEach
    public void testSetUp(){

        codeConverter.initCodeConverter();

        owner = User.builder()
                .id(123)
                .username("mintuchel")
                .build();

        survey = Survey.builder()
                .id(123)
                .owner(owner)
                .createdAt(LocalDateTime.now())
                .endAt(LocalDateTime.now())
                .headCnt(100)
                .regionCode(7)
                .genderCode(7)
                .ageCode(7)
                .point(10)
                .build();
    }

    @Test
    @DisplayName("설문 조회 성공")
    public void findSurveySuccess(){
        // given
        given(surveyRepository.findById(123)).willReturn(Optional.of(survey));

        // when
        SurveyInfoResponse response = surveyService.getSurveyById(123);

        // then
        Assertions.assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("설문 의뢰 성공")
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
        Assertions.assertThat(savedSurvey.getRegionCode()).isEqualTo(14);

        Assertions.assertThat(owner.getRequestedSurveyList()).hasSize(1);
    }

    @Test
    @DisplayName("참여 가능한 설문 조회 성공")
    public void getAvailableSurveySuccess() throws JsonProcessingException{
        // given
    }

    private SurveyDTO surveyDTO() throws JsonProcessingException {
        String jsonString = "{\n"
                + "  \"ownerId\": 123,\n"
                + "  \"selectedRegion\": [\"서울\", \"경기\", \"인천\"],\n"
                + "  \"selectedJob\": [\"개발\", \"스포츠\", \"기획\"],\n"
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
