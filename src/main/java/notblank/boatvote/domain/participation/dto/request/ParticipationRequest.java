package notblank.boatvote.domain.participation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ParticipationRequest(
    @NotBlank(message = "유저 아이디는 필수 입니다") int uid,
    @NotBlank(message = "설문 아이디는 필수 입니다") int sid
) { }
