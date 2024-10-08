package notblank.boatvote.survey.service;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {
    @InjectMocks
    private SurveyService surveyService;

    @Mock
    private SurveyRepository surveyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CodeConverter codeConverter;

    private User owner;
    private Survey survey;

    @BeforeEach
    public void testSetUp(){
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

        // when
        surveyService.addNewSurvey(surveyDTO());

        // then
        Assertions.assertThat(owner.getRequestedSurveyList()).hasSize(1);
    }

    private SurveyDTO surveyDTO() throws JsonProcessingException {
        String json = "{\n" +
                "  \"ownerId\": 123,\n" +
                "  \"selectedRegion\": [\"서울\"],\n" +
                "  \"selectedJob\": [\"개발\"],\n" +
                "  \"selectedGender\": [\"남자\"],\n" +
                "  \"selectedAge\": [\"20대\", \"30대\"],\n" +
                "  \"headCnt\": 1000,\n" +
                "  \"duration\": 5,\n" +
                "  \"description\": \"this is survey description\",\n" +
                "  \"questionList\": [\n" +
                "    {\n" +
                "      \"title\": \"성실히 대답할꺼지?\",\n" +
                "      \"optionList\": [\n" +
                "        { \"text\": \"ㅇㅇ\" },\n" +
                "        { \"text\": \"ㄴㄴ\" }\n" +
                "      ],\n" +
                "      \"isMultipleAnswer\": false,\n" +
                "      \"questionType\": \"MC\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"너 이름 적어\",\n" +
                "      \"optionList\": [],\n" +
                "      \"isMultipleAnswer\": false,\n" +
                "      \"questionType\": \"SA\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"니 어디사냐?\",\n" +
                "      \"optionList\": [\n" +
                "        { \"text\": \"서울\" },\n" +
                "        { \"text\": \"부산\" }\n" +
                "      ],\n" +
                "      \"isMultipleAnswer\": true,\n" +
                "      \"questionType\": \"MC\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, SurveyDTO.class);
    }
}
