package notblank.boatvote.domain.survey.dto.internal;

import lombok.Builder;

import java.util.List;

@Builder
public record FilterDTO(
        List<String> regionList,
        List<String> jobList,
        List<String> genderList,
        List<String> ageList
) { }
