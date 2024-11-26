package notblank.surveyfarm.domain.survey.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import notblank.surveyfarm.domain.survey.dto.common.QuestionDTO;

import java.util.List;

@Builder
public record SurveyQuestionListResponse(
        @Valid @NotNull List<QuestionDTO> questions
) { }
