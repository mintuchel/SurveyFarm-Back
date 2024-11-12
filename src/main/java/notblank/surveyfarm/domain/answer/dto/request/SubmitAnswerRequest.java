package notblank.surveyfarm.domain.answer.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SubmitAnswerRequest(
    @NotBlank(message = "유저 아이디는 필수 입니다") int uid,
    @NotBlank(message = "질문 아이디는 필수 입니다") int qid,
    @NotBlank(message = "답변은 필수 입니다") String text
) { }
