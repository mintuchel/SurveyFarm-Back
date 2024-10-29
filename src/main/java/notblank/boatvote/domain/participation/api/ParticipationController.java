package notblank.boatvote.domain.participation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "참여내역 API", description = "설문 참여내역 등록, 특정 유저의 참여내역 확인")
public class ParticipationController {

    private final ParticipationService participationService;

    @PostMapping()
    @Operation(summary = "특정 설문에 대한 유저의 참여내역 등록")
    public LocalDateTime participateSurvey(@RequestBody ParticipationRequest participationRequest) {
        return participationService.addNewParticipation(participationRequest);
    }

    @GetMapping("/history/{uid}")
    @Operation(summary = "유저가 참여한 설문내역 조회")
    public List<SurveyInfoResponse> getUserParticipatedSurveys(@PathVariable int uid) {
        return participationService.getParticipatedSurveyByUser(uid);
    }
}
