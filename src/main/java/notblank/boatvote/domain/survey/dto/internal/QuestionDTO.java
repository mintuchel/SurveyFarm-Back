package notblank.boatvote.domain.survey.dto.internal;

import lombok.Builder;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record QuestionDTO(
        int qid,
        String title,
        List<OptionDTO> optionList,
        boolean isMultipleAnswer,
        QuestionType questionType
) { }
