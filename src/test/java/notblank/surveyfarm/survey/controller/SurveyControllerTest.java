package notblank.surveyfarm.survey.controller;

import notblank.surveyfarm.SurveyFarmDataFactory;
import notblank.surveyfarm.domain.survey.api.SurveyController;
import notblank.surveyfarm.domain.survey.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Controller 단에서 JWT 테스트 ???
// 가능하다고 한다. JWT 구현해서 아래처럼 테스트해보기

@ExtendWith(MockitoExtension.class)
public class SurveyControllerTest {
    @InjectMocks
    private SurveyController controller;

    @Mock
    private SurveyService surveyService;

    private MockMvc mockMvc;

    @BeforeEach
    public void testSetUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void createNewSurveySuccess() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/survey")
                        .content(SurveyFarmDataFactory.getCreateSurveyRequestJsonString())
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk()) // 응답에 대한 검증수행
                .andDo(print()) // 요청과 응답의 내용을 콘솔에 출력
                .andReturn(); // 테스트 실행 후 결과를 MvcResult 객체로 반환
    }
}
