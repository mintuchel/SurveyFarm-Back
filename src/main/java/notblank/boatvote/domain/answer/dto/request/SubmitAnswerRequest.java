package notblank.boatvote.domain.answer.dto.request;

import notblank.boatvote.domain.question.entity.QuestionType;

public record SubmitAnswerRequest(
    String nickName,
    int qid,
    String text
) { }
