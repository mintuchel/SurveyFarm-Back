package notblank.surveyfarm.redis.repository;

import notblank.surveyfarm.domain.redis.deadlineSurvey.dto.DeadLineSurvey;
import notblank.surveyfarm.domain.redis.deadlineSurvey.repository.DeadLineSurveyRedisRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisRepositoryTest {
    @Autowired
    private DeadLineSurveyRedisRepository redisRepository;

    // Redis 는 롤백되지 않아 테스트때 저장한거 모두 삭제
    @AfterEach
    void tearDown() {
        redisRepository.deleteAll();
    }

    @Test
    @DisplayName("마감임박설문 저장 성공")
    void deadLineSurveySaveSuccess(){
        DeadLineSurvey deadLineSurvey = DeadLineSurvey.builder()
                .id(1003)
                .daysLeft(2)
                .build();

        redisRepository.save(deadLineSurvey);

        DeadLineSurvey returned = redisRepository.findById(deadLineSurvey.getId()).orElseThrow();

        Assertions.assertThat(returned.getId()).isEqualTo(deadLineSurvey.getId());
        Assertions.assertThat(returned.getDaysLeft()).isEqualTo(2);
    }
}
