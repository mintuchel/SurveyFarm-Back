package notblank.boatvote.domain.question.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public Question findQuestionById(int qid){
        return questionRepository.findById(qid).orElseThrow();
    }

    @Transactional(readOnly = true)
    public QuestionType getQuestionTypeById(int qid){
        return questionRepository.getQuestionTypeByQid(qid);
    }
}
