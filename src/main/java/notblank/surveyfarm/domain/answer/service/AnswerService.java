package notblank.surveyfarm.domain.answer.service;

import lombok.RequiredArgsConstructor;
import notblank.surveyfarm.domain.answer.dto.request.SubmitAnswerRequest;
import notblank.surveyfarm.domain.answer.dto.response.*;
import notblank.surveyfarm.domain.answer.entity.Answer;
import notblank.surveyfarm.domain.answer.repository.AnswerRepository;
import notblank.surveyfarm.domain.question.entity.QuestionType;
import notblank.surveyfarm.domain.question.service.QuestionService;
import notblank.surveyfarm.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final UserService userService;
    private final QuestionService questionService;

    private final AnswerRepository answerRepository;

    @Transactional
    public boolean submitAnswer(SubmitAnswerRequest submitAnswerRequest) {

        Answer answer = Answer.builder()
                .participant(userService.findById(submitAnswerRequest.uid()))
                .question(questionService.findQuestionById(submitAnswerRequest.qid()))
                .text(submitAnswerRequest.text())
                .build();

        answerRepository.save(answer);

        return true;
    }

    // 한 개의 Question에 대한 특정 Participant의 답변
    @Transactional(readOnly = true)
    public List<UserAnswerResponse> getParticipantAnswer(int uid, int qid){
        return answerRepository.findAnswersByUidAndQid(uid, qid).stream()
                .map(answer -> new UserAnswerResponse(answer.getText()))
                .toList();
    }

    // 한 개의 Question에 대한 User들의 답변 결과
    @Transactional(readOnly = true)
    public AnswerResultResponse getQuestionResult(int qid){
        QuestionType type = questionService.getQuestionTypeById(qid);

        if(type.equals(QuestionType.MC)){
            return new AnswerResultResponse(answerRepository.getMCQuestionResult(qid));
        }else{
            return new AnswerResultResponse(answerRepository.getSAQuestionResult(qid));
        }
    }
}
