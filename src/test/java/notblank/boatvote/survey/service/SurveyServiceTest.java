package notblank.boatvote.survey.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import notblank.boatvote.domain.survey.dto.request.OptionDTO;
import notblank.boatvote.domain.survey.dto.request.QuestionDTO;
import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.question.repository.OptionRepository;
import notblank.boatvote.domain.question.repository.QuestionRepository;
import notblank.boatvote.domain.survey.dto.request.SurveyDTO;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.survey.repository.SurveyRepository;
import notblank.boatvote.domain.survey.service.SurveyService;
import notblank.boatvote.domain.user.entity.User;

import notblank.boatvote.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class SurveyServiceTest {
    @Autowired private SurveyService surveyService;
    @Autowired  private SurveyRepository surveyRepository;

    @Autowired  private EntityManager em;

    @Autowired private QuestionRepository questionRepository;
    @Autowired private OptionRepository optionRepository;
    @Autowired private UserRepository userRepository;

    private User owner;
    private Survey survey;
    private Question question;
    private Option option1;
    private Option option2;

    private void userSetUp() {
        owner = User.builder()
                .password("1234")
                .username("mintuchel")
                .build();

        em.persist(owner);
    }

    private void optionSetUp(){
        option1 = Option.builder()
                .text("chelsea")
                .cnt(0)
                .build();

        option2 = Option.builder()
                .text("arsenal")
                .cnt(0)
                .build();
    }

    private void questionSetUp(){
        question = Question.builder()
                .title("응원하는 팀 고르셈")
                .type(QuestionType.MC)
                .isMultipleChoiceAvailable(false)
                .build();

        question.getOptionList().add(option1);
        question.getOptionList().add(option2);
    }

    @BeforeEach
    private void surveySetUp() {
        userSetUp();
        optionSetUp();
        questionSetUp();

        survey = Survey.builder()
                .owner(owner)
                .endAt(LocalDateTime.of(2024, 12, 31, 23, 59))
                .build();

        survey.getQuestionList().add(question);
    }

    @Test
    @Transactional
    @Rollback
    public void addSurveyTest(){
        em.persist(survey);

        // surveyService.save(survey);

        Survey s = surveyRepository.findById(survey.getId()).orElseThrow();

        List<Question> questionList = s.getQuestionList();
        Assertions.assertThat(questionList).hasSize(1);

        List<Option> optionList = questionList.get(0).getOptionList();
        Assertions.assertThat(optionList).hasSize(2);

        Question q = questionRepository.findById(questionList.get(0).getId()).orElseThrow();

        Option o1 = optionRepository.findById(optionList.get(0).getId()).orElseThrow();
        Option o2 = optionRepository.findById(optionList.get(1).getId()).orElseThrow();
        Assertions.assertThat(o1.getText()).isEqualTo("chelsea");
        Assertions.assertThat(o2.getText()).isEqualTo("arsenal");

        User user = userRepository.findById(owner.getId()).orElseThrow();
        Assertions.assertThat(user.getId()).isEqualTo(s.getOwner().getId());
    }

    @Test
    @Transactional
    @Rollback
    public void addSurveyByDTOTest() throws JsonProcessingException {

        String json = "{\n" +
                "  \"ownerId\": 123,\n" +
                "  \"selectedRegion\": [\"서울\", \"제주\"],\n" +
                "  \"selectedJob\": [\"개발\"],\n" +
                "  \"selectedGender\": [],\n" +
                "  \"selectedAge\": [\"20대\", \"30대\"],\n" +
                "  \"selectedHeadCnt\": [\"500명\"],\n" +
                "  \"description\": \"this is survey description\",\n" +
                "  \"questionList\": [\n" +
                "    {\n" +
                "      \"title\": \"니 어디사냐?\",\n" +
                "      \"optionList\": [\n" +
                "        { \"text\": \"부산\" },\n" +
                "        { \"text\": \"서울\" }\n" +
                "      ],\n" +
                "      \"questionType\": \"MC\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"what is your name?\",\n" +
                "      \"optionList\": [],\n" +
                "      \"questionType\": \"SA\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"니 뭐꼬\",\n" +
                "      \"optionList\": [\n" +
                "        { \"text\": \"황자르\" },\n" +
                "        { \"text\": \"즐라탄\" }\n" +
                "      ],\n" +
                "      \"questionType\": \"MC\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        SurveyDTO surveyDTO = objectMapper.readValue(json, SurveyDTO.class);

//        int ownerId = surveyDTO.ownerId();
//        User owner = userRepository.findById(ownerId).orElseThrow();

        Survey curSurvey = Survey.builder()
                .owner(owner)
                .point(100)
                .build();

        // 의뢰받은 QuestionDTO들 빼내기
        List<QuestionDTO> questionDTOList = surveyDTO.questionList();

        for(QuestionDTO curQuestionDTO : questionDTOList){
            String curTitle = curQuestionDTO.title();
            QuestionType curType = curQuestionDTO.questionType();

            Question curQuestion = Question.builder()
                    .title(curTitle)
                    .type(curType)
                    .build();

            if(curType == QuestionType.MC) {
                List<OptionDTO> optionList = curQuestionDTO.optionList();
                for (OptionDTO curOptionDTO: optionList) {
                    Option option = Option.builder()
                            .text(curOptionDTO.text())
                            .cnt(0)
                            .build();
                    curQuestion.getOptionList().add(option);
                }
            }

            curSurvey.getQuestionList().add(curQuestion);
        }

        surveyRepository.save(curSurvey);

        Assertions.assertThat(curSurvey.getId()).isNotNull();
        Assertions.assertThat(curSurvey.getQuestionList()).hasSize(3);
        Assertions.assertThat(curSurvey.getCreatedAt()).isNotNull();

        Assertions.assertThat(curSurvey.getQuestionList().get(0).getOptionList()).hasSize(2);
        Assertions.assertThat(curSurvey.getQuestionList().get(1).getType()).isEqualTo(QuestionType.SA);
        Assertions.assertThat(curSurvey.getQuestionList().get(2).getId()).isNotNull();
    }
}
