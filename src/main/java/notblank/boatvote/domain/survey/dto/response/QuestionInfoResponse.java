package notblank.boatvote.domain.survey.dto.response;

import notblank.boatvote.domain.question.entity.QuestionType;

import java.util.List;

public record QuestionInfoResponse(
        int qid,
        String title,
        List<OptionInfoResponse> optionList,
        boolean isMultipleAnswer,
        QuestionType questionType
) { }