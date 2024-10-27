package notblank.boatvote.domain.participatedSurvey.api;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.participatedSurvey.dto.request.ParticipateRequest;
import notblank.boatvote.domain.participatedSurvey.service.ParticipatedSurveyService;
import notblank.boatvote.domain.survey.dto.response.SurveyInfoResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participate")
public class ParticipatedSurveyController {

    private final ParticipatedSurveyService participatedSurveyService;

    @PostMapping()
    public LocalDateTime participateSurvey(@RequestBody ParticipateRequest participateRequest) {
        return participatedSurveyService.participateSurvey(participateRequest);
    }

    @GetMapping("/history")
    public List<SurveyInfoResponse> getParticipatedSurveys(@RequestParam int uid) {
        return participatedSurveyService.getParticipatedSurveys(uid);
    }
}
