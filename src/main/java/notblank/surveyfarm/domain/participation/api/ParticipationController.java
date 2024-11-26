package notblank.surveyfarm.domain.participation.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notblank.surveyfarm.domain.participation.dto.request.ParticipationRequest;
import notblank.surveyfarm.domain.participation.service.ParticipationService;
import notblank.surveyfarm.domain.survey.dto.response.SurveyInfoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/participation")
@Tag(name = "참여내역 API", description = "참여내역 등록, 참여내역 확인")
public class ParticipationController {

    private final ParticipationService participationService;

    @PostMapping()
    @Operation(summary = "특정 설문에 대한 유저의 참여내역 등록")
    public String createParticipation(@RequestBody @Valid ParticipationRequest participationRequest) {
        return participationService.addNewParticipation(participationRequest);
    }

    @GetMapping("/{uid}")
    @Operation(summary = "유저가 참여한 설문 조회")
    public List<SurveyInfoResponse> getParticipatedSurveys(@PathVariable int uid) {
        return participationService.getParticipatedSurveys(uid);
    }

    // 이거 개대충 만들어서 손봐야함
    // 나중에 꼭 손보자 재홍아
    // DTO랑 Repository 쿼리문 JPQL로 한거도 다 손봐야함
    // 네이밍도 ㅇㅇㅇ
    @GetMapping("")
    @Operation(summary = "유저가 특정 설문에 참여한 시간 조회")
    public String getParticipatedDate(@RequestParam int uid, @RequestParam int sid) {
        return participationService.getParticipatedTime(uid, sid);
    }
}
