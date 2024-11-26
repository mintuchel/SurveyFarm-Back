package notblank.surveyfarm.domain.survey.dto.common;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record OptionDTO(
    @NotBlank String text
) { }
