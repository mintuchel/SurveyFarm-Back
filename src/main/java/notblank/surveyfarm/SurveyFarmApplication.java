package notblank.surveyfarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SurveyFarmApplication {
	public static void main(String[] args) {
		SpringApplication.run(SurveyFarmApplication.class, args);
	}
}