package notblank.boatvote.domain.question.service;

import notblank.boatvote.domain.question.entity.Option;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    public Question findOne(int qid){
        return questionRepository.findById(qid).orElseThrow();
    }

    public void addOption(Question question, Option option){
        question.getOptionList().add(option);
    }
}
