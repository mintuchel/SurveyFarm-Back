package notblank.surveyfarm.domain.survey.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import notblank.surveyfarm.domain.question.entity.QuestionType;

import java.util.List;

@Builder
public record QuestionDTO(
        int qid,
        @NotBlank String title,
        @NotNull List<OptionDTO> optionList,
        @NotBlank boolean isMultipleAnswer,
        @NotBlank QuestionType questionType
) { }
