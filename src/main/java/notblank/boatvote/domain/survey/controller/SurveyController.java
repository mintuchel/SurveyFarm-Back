package notblank.boatvote.domain.survey.controller;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.survey.dto.SurveyDTO;
import notblank.boatvote.domain.survey.service.SurveyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyController {
    private final SurveyService surveyService;

//    @GetMapping("/{surveyId}")
//    public SurveyDTO getSurvey(@PathVariable int surveyId){
//        return surveyService.getSurveyById(surveyId);
//    }

    @PostMapping()
    public int requestSurvey(@RequestBody SurveyDTO surveyDTO){
        return surveyService.addNewSurvey(surveyDTO);
    }
}