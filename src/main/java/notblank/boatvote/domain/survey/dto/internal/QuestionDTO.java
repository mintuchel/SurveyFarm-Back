package notblank.boatvote.domain.survey.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record QuestionDTO(
        int qid,
        @NotBlank String title,
        @NotNull List<OptionDTO> optionList,
        @NotBlank boolean isMultipleAnswer,
        @NotBlank QuestionType questionType
) { }
