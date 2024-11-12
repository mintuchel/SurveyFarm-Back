package notblank.surveyfarm.domain.dummy;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import notblank.surveyfarm.domain.dummy.initializer.EnterpriseSurveyGenerator;
import notblank.surveyfarm.domain.dummy.initializer.OrganizationSurveyGenerator;
import notblank.surveyfarm.domain.dummy.initializer.PersonalSurveyGenerator;
import notblank.surveyfarm.domain.dummy.initializer.UserGenerator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DummyGenerator {

    private final UserGenerator userGenerator;
    private final PersonalSurveyGenerator personalSurveyGenerator;
    private final OrganizationSurveyGenerator organizationSurveyGenerator;
    private final EnterpriseSurveyGenerator enterpriseSurveyGenerator;

    @PostConstruct
    public void generateDummyUser(){

        userGenerator.generateUsers();

        personalSurveyGenerator.generatePersonalSurveys();
        organizationSurveyGenerator.generateOrganizationSurveys();
        enterpriseSurveyGenerator.generateEnterpriseSurveys();
    }
}
