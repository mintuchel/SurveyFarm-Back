package notblank.boatvote.domain.participation.api;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.participation.dto.request.ParticipationRequest;
import notblank.boatvote.domain.participation.service.ParticipationService;
import notblank.boatvote.domain.survey.dto.response.SurveyInfoResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participation")
public class ParticipationController {

    private final ParticipationService participationService;

    @PostMapping()
    public LocalDateTime participateSurvey(@RequestBody ParticipationRequest participationRequest) {
        return participationService.addNewParticipation(participationRequest);
    }

    @GetMapping("/history")
    public List<SurveyInfoResponse> getUserParticipatedSurveys(@RequestParam int uid) {
        return participationService.getParticipatedSurveyByUser(uid);
    }
}
