package notblank.boatvote.domain.survey.dto.request;

import java.util.List;

public record NewSurveyRequest(
    int ownerId,
    String title,
    String description,
    String imgUrl,
    int duration,
    int maxHeadCnt,
    List<String> regionList,
    List<String> jobList,
    List<String> genderList,
    List<String> ageList,
    List<NewQuestionRequest> questionList
) { }
