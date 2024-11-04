package notblank.boatvote.domain.survey.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.survey.dto.request.CreateSurveyRequest;
import notblank.boatvote.domain.survey.dto.response.SurveyResponse;
import notblank.boatvote.domain.survey.service.SurveyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
@Tag(name = "설문 API", description = "설문 생성, 설문 조회")
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping("")
    @Operation(summary = "설문 전체 조회")
    public List<SurveyResponse> getAllSurveys() {
        return surveyService.getAllSurveys();
    }

    @GetMapping("/{sid}")
    @Operation(summary = "설문 단건 조회")
    public SurveyResponse getSurveyById(@PathVariable int sid){
        return surveyService.getSurveyResponseById(sid);
    }

    @PostMapping()
    @Operation(summary = "설문 생성")
    public int createSurvey(@RequestBody CreateSurveyRequest createSurveyRequest){
        return surveyService.addNewSurvey(createSurveyRequest);
    }

    @GetMapping("/available/{uid}")
    @Operation(summary = "특정 유저가 참여가능한 설문 조회")
    public List<SurveyResponse> getAvailableSurveys(@PathVariable int uid){
        return surveyService.getAvailableSurveys(uid);
    }

    // event-scheduler 사용
    // deadline table은 스키마로 작성해두고 실제 엔티티는 필요가 없음
    // 근데 문제는 sid를 알려면 survey 테이블이 생성된 후 해야하는데 schema.sql 로 하면
    // survey 테이블이 생성되기 전에 만들어져서 에러가 터진다는 것 ㅇㅇ
    // redis table cache 사용
//    @GetMapping("/deadline")
//    public List<SurveyResponse> getDeadLineSurveys(){
//        return surveyService.getDeadLineSurveys();
//    }

    @GetMapping("/requested/{uid}")
    @Operation(summary = "특정 유저가 의뢰한 설문 조회")
    public List<SurveyResponse> getRequestedSurveys(@PathVariable("uid") int uid) {
        return surveyService.getRequestedSurveys(uid);
    }
}