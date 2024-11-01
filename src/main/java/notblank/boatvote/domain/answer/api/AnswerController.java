package notblank.boatvote.domain.answer.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "답변 API", description = "답변 저장, 답변 조회, 답변 통계 조회")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("")
    @Operation(summary = "특정 질문에 대한 유저의 답변 저장")
    public boolean submitAnswer(@RequestBody @Valid SubmitAnswerRequest submitAnswerRequest){
        return answerService.submitAnswer(submitAnswerRequest);
    }

    @GetMapping("")
    @Operation(summary = "특정 질문에 대한 유저의 답변 조회")
    public List<UserAnswerResponse> getCertainUserResult(@RequestParam int uid, @RequestParam int qid){
        return answerService.getParticipantAnswer(uid, qid);
    }

    @GetMapping("/result/{qid}")
    @Operation(summary = "특정 질문에 대한 통계 조회")
    public AnswerResultResponse getQuestionResult(@PathVariable int qid){
        return answerService.getQuestionResult(qid);
    }
}
