package notblank.boatvote.domain.survey.dto.request;

import notblank.boatvote.domain.question.entity.QuestionType;

import java.util.List;

public record QuestionDTO(
        String title,
        List<OptionDTO> optionList,
        boolean isMultipleAnswer,
        QuestionType questionType
) { }
