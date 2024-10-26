package notblank.boatvote.domain.survey.dto.request;

import java.util.List;

public record NewSurveyRequest(
    int ownerId,
    List<String> selectedRegion,
    List<String> selectedJob,
    List<String> selectedGender,
    List<String> selectedAge,
    int selectedHeadCnt,
    int selectedDuration,
    String description,
    List<NewQuestionRequest> questionList
) { }
