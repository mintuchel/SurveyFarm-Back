package notblank.boatvote.domain.survey.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record SurveyInfoResponse(
        int sid,
        int ownerId,
        List<String> selectedRegion,
        List<String> selectedJob,
        List<String> selectedGender,
        List<String> selectedAge,
        int headCnt,
        int point,
        LocalDateTime endAt,
        String description,
        List<QuestionInfoResponse> questionList
) { }
