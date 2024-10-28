package notblank.boatvote.domain.answer.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.answer.dto.request.SubmitAnswerRequest;
import notblank.boatvote.domain.answer.dto.response.AnswerResultResponse;
import notblank.boatvote.domain.answer.dto.response.UserAnswerResponse;
import notblank.boatvote.domain.answer.service.AnswerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
@Tag(name = "Answer API", description = "설문 참여, 특정 유저의 답변 확인, 특정 질문의 답변 결과 확인")
public class AnswerController {

    private final AnswerService answerService;

    // 특정 유저의 답변 저장
    @PostMapping("")
    public boolean submitAnswer(@RequestBody SubmitAnswerRequest submitAnswerRequest){
        return answerService.submitAnswer(submitAnswerRequest);
    }

    @GetMapping("")
    public List<UserAnswerResponse> getCertainUserResult(@RequestParam int uid, @RequestParam int qid){
        return answerService.getParticipantAnswer(uid, qid);
    }

    @GetMapping("/result")
    public AnswerResultResponse getQuestionResult(@RequestParam int qid){
        return answerService.getQuestionResult(qid);
    }
}
