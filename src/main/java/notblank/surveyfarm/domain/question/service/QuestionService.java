package notblank.surveyfarm.domain.question.service;

import lombok.RequiredArgsConstructor;
import notblank.surveyfarm.domain.question.entity.Question;
import notblank.surveyfarm.domain.question.entity.QuestionType;
import notblank.surveyfarm.domain.question.repository.QuestionRepository;
import notblank.surveyfarm.global.exception.errorcode.SurveyErrorCode;
import notblank.surveyfarm.global.exception.exception.SurveyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public Question findQuestionById(int qid){
        return questionRepository.findById(qid)
                .orElseThrow(() -> new SurveyException(SurveyErrorCode.QUESTION_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public QuestionType getQuestionTypeById(int qid){
        return questionRepository.getQuestionTypeByQid(qid);
    }
}