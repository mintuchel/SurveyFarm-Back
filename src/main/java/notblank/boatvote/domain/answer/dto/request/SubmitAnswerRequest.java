package notblank.boatvote.domain.answer.dto.request;

import notblank.boatvote.domain.question.entity.QuestionType;

public record SubmitAnswerRequest(
    int uid,
    int qid,
    QuestionType type,
    String answer
) { }
