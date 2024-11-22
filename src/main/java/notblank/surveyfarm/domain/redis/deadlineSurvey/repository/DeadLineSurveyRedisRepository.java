package notblank.surveyfarm.domain.redis.deadlineSurvey.repository;

import notblank.surveyfarm.domain.redis.deadlineSurvey.dto.DeadLineSurvey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// DeadLineSurvey 자체가 @RedisHash 로 정의되어 Redis 에서 사용됨을 명시하고 있기 때문에
// 이 Repository 로 작업을 하면 자동으로 Redis 를 통해 인메모리 캐시에 저장됨
@Repository
public interface DeadLineSurveyRedisRepository extends CrudRepository<DeadLineSurvey, Integer> {
}

