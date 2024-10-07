package notblank.boatvote.domain.survey.dto.request;

import java.util.List;

public record SurveyDTO(
    int ownerId,
    List<String> selectedRegion,
    List<String> selectedJob,
    List<String> selectedGender,
    List<String> selectedAge,
    List<String> selectedHeadCnt,
    String description,
    List<QuestionDTO> questionList
) { }
