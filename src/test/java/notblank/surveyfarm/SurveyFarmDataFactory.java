package notblank.surveyfarm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import notblank.surveyfarm.domain.survey.dto.request.CreateSurveyRequest;

public class SurveyFarmDataFactory {

    public static CreateSurveyRequest getCreateSurveyRequestDTO() throws JsonProcessingException {
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
                + "      \"isMultipleChoice\": false,\n"
                + "      \"questionType\": \"MC\"\n"
                + "    },\n"
                + "    {\n"
                + "      \"qid\": 2,\n"
                + "      \"title\": \"최근 5경기 니콜라스 잭슨의 폼에 대해 너의 의견을 적어줘\",\n"
                + "      \"optionList\": [],\n"
                + "      \"isMultipleChoice\": false,\n"
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
                + "      \"isMultipleChoice\": true,\n"
                + "      \"questionType\": \"MC\"\n"
                + "    }\n"
                + "  ]\n"
                + "}";

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, CreateSurveyRequest.class);
    }

}
