package notblank.surveyfarm.domain.participation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notblank.surveyfarm.domain.participation.dto.request.ParticipationRequest;
import notblank.surveyfarm.domain.participation.service.ParticipationService;
import notblank.surveyfarm.domain.survey.dto.response.SurveyResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/participation")
@Tag(name = "참여내역 API", description = "참여내역 등록, 참여내역 확인")
public class ParticipationController {

    private final ParticipationService participationService;

    @PostMapping()
    @Operation(summary = "특정 설문에 대한 유저의 참여내역 등록")
    public String participateSurvey(@RequestBody @Valid ParticipationRequest participationRequest) {
        return participationService.addNewParticipation(participationRequest);
    }

    @GetMapping("/{uid}")
    @Operation(summary = "유저가 참여한 설문 조회")
    public List<SurveyResponse> getUserParticipatedSurveys(@PathVariable int uid) {
        return participationService.getParticipatedSurveyByUser(uid);
    }
}
