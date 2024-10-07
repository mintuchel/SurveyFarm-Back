package notblank.boatvote.domain.survey.dto.request;

import java.util.List;

public record SurveyDTO(
    int ownerId,
    List<String> selectedRegion,
    List<String> selectedJob,
    List<String> selectedGender,
    List<String> selectedAge,
    int headCnt,
    int duration,
    String description,
    List<QuestionDTO> questionList
) { }
