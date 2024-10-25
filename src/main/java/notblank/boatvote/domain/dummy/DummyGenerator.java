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
    }
}
