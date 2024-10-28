package notblank.boatvote.domain.survey.dto.request;

import java.util.List;

public record NewSurveyRequest(
    int ownerId,
    String title,
    String imgUrl,
    List<String> regionList,
    List<String> jobList,
    List<String> genderList,
    List<String> ageList,
    int maxHeadCnt,
    int duration,
    String description,
    List<NewQuestionRequest> questionList
) { }
