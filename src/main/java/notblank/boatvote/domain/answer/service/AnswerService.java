package notblank.boatvote.domain.answer.service;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.answer.dto.request.SubmitAnswerRequest;
import notblank.boatvote.domain.answer.dto.response.*;
import notblank.boatvote.domain.answer.entity.Answer;
import notblank.boatvote.domain.answer.repository.AnswerRepository;
import notblank.boatvote.domain.question.entity.QuestionType;
import notblank.boatvote.domain.question.service.QuestionService;
import notblank.boatvote.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final UserService userService;
    private final QuestionService questionService;

    private final AnswerRepository answerRepository;

    @Transactional
    public boolean submitAnswer(SubmitAnswerRequest submitAnswerRequest) {
        int uid = submitAnswerRequest.uid();
        int qid = submitAnswerRequest.qid();

        Answer answer = Answer.builder()
                .user(userService.findUserById(uid))
                .question(questionService.findQuestionById(qid))
                .questionType(submitAnswerRequest.type())
                .answer(submitAnswerRequest.answer())
                .build();

        answerRepository.save(answer);

        return true;
    }

    // 한 개의 Question에 대한 특정 User의 답변
    @Transactional(readOnly = true)
    public UserAnswerResponse getUserAnswer(int uid, int qid){
        return answerRepository.findByUserIdAndQuestionId(uid, qid)
                .map(answer -> new UserAnswerResponse(answer.getAnswer()))
                .orElseThrow();
    }

    // 한 개의 Question에 대한 User들의 답변 결과
    @Transactional(readOnly = true)
    public TempResponse getQuestionResult(int qid){
//        QuestionType type = questionService.getQuestionTypeById(qid);
//
//        if(type.equals(QuestionType.MC)){
//            List<Integer> mcResultList = answerRepository.getMCQuestionResult(qid);
//            return new MCResultResponse(mcResultList);
//        }else{
//            List<String> saResultList = answerRepository.getSAQuestionResult(qid);
//            return new SAResultResponse(saResultList);
//        }

        QuestionType type = questionService.getQuestionTypeById(qid);

        if(type.equals(QuestionType.MC)){
            return new TempResponse(answerRepository.getMCQuestionResult(qid));
        }else{
            return new TempResponse(answerRepository.getSAQuestionResult(qid));
        }
    }
}
