package notblank.boatvote.domain.survey.dto.internal;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import notblank.boatvote.domain.question.entity.Option;

@Builder
public record OptionDTO(
    @NotBlank String text
) { }
