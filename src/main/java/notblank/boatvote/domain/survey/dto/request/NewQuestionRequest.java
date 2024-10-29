package notblank.boatvote.domain.survey.dto.request;

import notblank.boatvote.domain.question.entity.QuestionType;

import java.util.List;

public record NewQuestionRequest(
        String title,
        List<NewOptionRequest> optionList,
        boolean isMultipleAnswer,
        QuestionType questionType
) { }
