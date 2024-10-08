package notblank.boatvote.survey.api;

import notblank.boatvote.domain.survey.api.SurveyController;
import notblank.boatvote.domain.survey.service.SurveyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SurveyController.class)
public class SurveyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyService surveyService;

    @Test
    public void requestSurveyTest(){

    }

    @Test
    public void getSurveyTest(){

    }
}
