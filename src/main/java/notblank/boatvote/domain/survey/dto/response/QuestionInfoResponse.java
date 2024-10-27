package notblank.boatvote.domain.survey.dto.response;

import notblank.boatvote.domain.question.entity.QuestionType;

import java.util.List;

public record QuestionInfoResponse(
        int qid, // 질문 고유 아이디
        String title, // 질문
        List<OptionInfoResponse> optionList, // 객관식 선지 List
        boolean isMultipleAnswer, // 중복선택가능여부
        QuestionType questionType // 질문유형 (MC, SA)
) { }