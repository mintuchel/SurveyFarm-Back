package notblank.boatvote.domain.survey.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.survey.dto.request.NewSurveyRequest;
import notblank.boatvote.domain.survey.dto.response.SurveyInfoResponse;
import notblank.boatvote.domain.survey.service.SurveyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
@Tag(name = "Survey API", description = "설문 추가, 설문 단건 조회, 참여가능 설문 조회")
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping("/{surveyId}")
    public SurveyInfoResponse getSurveyById(@PathVariable int surveyId){
        return surveyService.getSurveyInfoResponseById(surveyId);
    }

    @GetMapping("/available/{participantId}")
    public List<SurveyInfoResponse> getAvailableSurveys(@PathVariable int participantId){
        return surveyService.getAvailableSurveys(participantId);
    }
    @PostMapping()
    public int requestSurvey(@RequestBody NewSurveyRequest newSurveyRequest){
        return surveyService.addNewSurvey(newSurveyRequest);
    }
}