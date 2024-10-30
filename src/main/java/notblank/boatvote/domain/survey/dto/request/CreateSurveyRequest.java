package notblank.boatvote.domain.survey.dto.request;

import notblank.boatvote.domain.survey.dto.internal.FilterDTO;
import notblank.boatvote.domain.survey.dto.internal.QuestionDTO;
import notblank.boatvote.domain.survey.dto.internal.SurveyInfoDTO;

import java.util.List;

public record CreateSurveyRequest(
    SurveyInfoDTO surveyInfo,
    FilterDTO filters,
    List<QuestionDTO> questions
) { }