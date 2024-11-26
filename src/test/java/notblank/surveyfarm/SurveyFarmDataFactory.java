package notblank.surveyfarm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import notblank.surveyfarm.domain.survey.dto.request.CreateSurveyRequest;

public class SurveyFarmDataFactory {

    public static String getCreateSurveyRequestJsonString() {
        String jsonString = """
        {
          "surveyInfo": {
            "uid" : 20, 
            "nickName": "Noel",
            "title": "Postman Sample Survey Title",
            "description": "This is a postman sample survey description",
            "imgUrl": "sampleImageUrl",
            "duration": 5,
            "maxHeadCnt": 1000
          },
          "filters": {
            "regionList": ["서울", "경기", "인천"],
            "jobList": ["기획·전략", "회계·세무"],
            "genderList": ["남자"],
            "ageList": ["10대", "20대"]
          },
          "questions": [
            {
              "title": "최애 첼시 선수는?",
              "optionList": [
                {"text": "파머"},
                {"text": "마두에케"},
                {"text": "엔조"},
                {"text": "카이세도"}
              ],
              "isMultipleAnswer": false,
              "questionType": "MC"
            },
            {
              "title": "최근 5경기 니콜라스 잭슨의 폼에 대해 너의 의견을 적어줘",
              "optionList": [],
              "isMultipleAnswer": false,
              "questionType": "SA"
            },
            {
              "title": "첼시에 영입하면 좋을거 같은 선수를 모두 골라",
              "optionList": [
                {"text": "손흥민"},
                {"text": "박지성"},
                {"text": "차범근"}
              ],
              "isMultipleAnswer": true,
              "questionType": "MC"
            }
          ]
        }
        """;

        return jsonString;
    }

    public static CreateSurveyRequest getCreateSurveyRequestDTO() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(getCreateSurveyRequestJsonString(), CreateSurveyRequest.class);
    }

}
