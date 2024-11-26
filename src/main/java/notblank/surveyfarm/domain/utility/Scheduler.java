package notblank.surveyfarm.domain.utility;

import lombok.RequiredArgsConstructor;
import notblank.surveyfarm.domain.survey.entity.Survey;
import notblank.surveyfarm.domain.survey.repository.SurveyRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final SurveyRepository surveyRepository;

    // UPDATE 작업이므로 Transactional 선언해줘야함
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void updateSurveyStatus(){
        List<Survey> surveys = surveyRepository.getInProgressSurveys();
        for(Survey survey : surveys) survey.updateStatus();
    }
}
