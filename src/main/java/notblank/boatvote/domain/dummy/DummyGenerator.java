package notblank.boatvote.domain.dummy;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DummyGenerator {

    private final DummyService dummyService;

    @PostConstruct
    public void generateDummyUser(){
        dummyService.initUser();

        dummyService.initSurvey1();
        dummyService.initSurvey2();
        dummyService.initSurvey3();
        dummyService.initSurvey4();
        dummyService.initSurvey5();
        dummyService.initSurvey6();
        dummyService.initSurvey7();
        dummyService.initSurvey8();
        dummyService.initSurvey9();
        dummyService.initSurvey10();

        dummyService.initParticipatedSurvey();
    }
}
