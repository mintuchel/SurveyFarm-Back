package notblank.surveyfarm.domain.survey.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import notblank.surveyfarm.domain.survey.dto.request.CreateSurveyRequest;
import notblank.surveyfarm.domain.survey.dto.response.SurveyFilterResponse;
import notblank.surveyfarm.domain.survey.dto.response.SurveyInfoResponse;
import notblank.surveyfarm.domain.survey.dto.response.SurveyQuestionListResponse;
import notblank.surveyfarm.domain.survey.service.SurveyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/survey")
@Tag(name = "설문 API", description = "설문 생성, 설문 조회")
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping()
    @Operation(summary = "새로운 설문 생성")
    public int createSurvey(@RequestBody CreateSurveyRequest createSurveyRequest){
        return surveyService.addNewSurvey(createSurveyRequest);
    }

    @GetMapping("/{sid}")
    @Operation(summary = "특정 설문 기본 정보 조회")
    public SurveyInfoResponse getSurveyInfoById(@PathVariable int sid){
        return surveyService.getSurveyInfoById(sid);
    }

    @GetMapping("/{sid}/tags")
    @Operation(summary = "특정 설문 태그 조회")
    public SurveyFilterResponse getSurveyFilterById(@PathVariable int sid){
        return surveyService.getSurveyFilterById(sid);
    }

    @GetMapping("/{sid}/questions")
    @Operation(summary = "특정 설문 질문 리스트 조회")
    public SurveyQuestionListResponse getSurveyQuestionsById(@PathVariable int sid){
        return surveyService.getSurveyQuestionListById(sid);
    }

    @GetMapping("/available/{uid}")
    @Operation(summary = "특정 유저가 참여가능한 설문 조회")
    public List<SurveyInfoResponse> getAvailableSurveys(@PathVariable int uid){
        return surveyService.getAvailableSurveys(uid);
    }

    @GetMapping("/requested/{uid}")
    @Operation(summary = "특정 유저가 의뢰한 설문 조회")
    public List<SurveyInfoResponse> getRequestedSurveys(@PathVariable("uid") int uid) {
        return surveyService.getRequestedSurveys(uid);
    }

    @GetMapping("/deadline")
    @Operation(summary = "마감임박 설문 조회")
    public List<SurveyInfoResponse> getDeadlineSurveys() {
        return surveyService.getDeadLineSurveys();
    }

    @GetMapping("/trending")
    @Operation(summary = "인기 설문 조회")
    public List<SurveyInfoResponse> getTrendingSurveys() {
        return surveyService.getTrendingSurveys();
    }
}