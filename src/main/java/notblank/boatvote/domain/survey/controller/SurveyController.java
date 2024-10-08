package notblank.boatvote.domain.survey.controller;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.survey.dto.request.SurveyDTO;
import notblank.boatvote.domain.survey.dto.response.SurveyInfoResponse;
import notblank.boatvote.domain.survey.service.SurveyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping("/{surveyId}")
    public SurveyInfoResponse getSurvey(@PathVariable int surveyId){
        return surveyService.getSurveyById(surveyId);
    }

    @PostMapping()
    public int requestSurvey(@RequestBody SurveyDTO surveyDTO){
        return surveyService.addNewSurvey(surveyDTO);
    }
}