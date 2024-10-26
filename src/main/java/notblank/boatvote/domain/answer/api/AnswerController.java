package notblank.boatvote.domain.answer.api;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.answer.service.AnswerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AnswerController {

    private AnswerService answerService;

    @GetMapping("/answer/{uid}/{sid}")
    public void getCertainUserResult(int uid, int sid){

    }

    public void getSurveyResult(int sid){

    }

}
