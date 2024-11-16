package notblank.surveyfarm.domain.user.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(
    @NotBlank int id,
    @NotBlank String password,
    @NotBlank String nickName,
    @NotNull @Size(min = 1) List<String> regionList,
    @NotNull @Size(min = 1) List<String> jobList,
    @NotNull @Size(min = 1) List<String> genderList,
    @NotNull @Size(min = 1) List<String> ageList
) { }
