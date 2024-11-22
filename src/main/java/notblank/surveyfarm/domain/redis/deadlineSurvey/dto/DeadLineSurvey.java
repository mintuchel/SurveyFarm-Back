package notblank.surveyfarm.domain.redis.deadlineSurvey.dto;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

// RedisHash 를 통해 Redis에 의해 서버의 인메모리 캐시에 저장될 객체라는 것을 명시
// 이 객체의 key 이름은 deadLine이고 Redis에 저장되는 시간은 86400초임 즉 하루임
// timetoLive 시간이 다 지나면 해당 객체는 알아서 캐시에서 삭제됨
@Builder
@Getter
@RedisHash(value = "deadline", timeToLive = 86400) // 해당 엔티티가 Redis 엔티티임을 명시
public class DeadLineSurvey {
    // Redis 는 @GeneratedValue 같은게 없어서 Id를 직접 관리해줘야함
    // @Id 어노테이션이 적용된 맴버변수에 값이 실제 Redis 의 HashID임
    @Id
    private int id;

    private int daysLeft;
}
