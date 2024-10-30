package notblank.boatvote.domain.survey.dto.internal;

import lombok.Builder;
import notblank.boatvote.domain.question.entity.Option;

@Builder
public record OptionDTO(
    String text
) { }
