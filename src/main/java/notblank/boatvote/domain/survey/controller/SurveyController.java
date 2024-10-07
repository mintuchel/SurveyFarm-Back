package notblank.boatvote.domain.survey.controller;

import lombok.RequiredArgsConstructor;
import notblank.boatvote.domain.survey.dto.request.SurveyDTO;
import notblank.boatvote.domain.survey.service.SurveyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyController {
    private final SurveyService surveyService;

    @PostMapping()
    public int requestSurvey(@RequestBody SurveyDTO surveyDTO){
        return surveyService.addNewSurvey(surveyDTO);
    }
}