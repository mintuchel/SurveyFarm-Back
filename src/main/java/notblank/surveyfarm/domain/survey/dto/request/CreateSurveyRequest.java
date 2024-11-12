package notblank.surveyfarm.domain.survey.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import notblank.surveyfarm.domain.survey.dto.internal.FilterDTO;
import notblank.surveyfarm.domain.survey.dto.internal.QuestionDTO;
import notblank.surveyfarm.domain.survey.dto.internal.SurveyInfoDTO;

import java.util.List;

public record CreateSurveyRequest(
    @Valid @NotNull SurveyInfoDTO surveyInfo,
    @Valid @NotNull FilterDTO filters,
    @Valid @NotNull @Size(min = 1) List<QuestionDTO> questions
) { }