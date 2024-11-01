package notblank.boatvote.domain.survey.dto.internal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record FilterDTO(
        @NotNull @Size(min = 1) List<String> regionList,
        @NotNull @Size(min = 1) List<String> jobList,
        @NotNull @Size(min = 1) List<String> genderList,
        @NotNull @Size(min = 1) List<String> ageList
) { }
