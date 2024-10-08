package notblank.boatvote.domain.survey.dto.response;

import notblank.boatvote.domain.survey.dto.request.QuestionDTO;

import java.time.LocalDateTime;
import java.util.List;

public record SurveyInfoResponse(
        int ownerId,
        List<String> selectedRegion,
        List<String> selectedJob,
        List<String> selectedGender,
        List<String> selectedAge,
        int headCnt,
        int point,
        LocalDateTime endAt,
        String description,
        List<QuestionDTO> questionList
) { }
