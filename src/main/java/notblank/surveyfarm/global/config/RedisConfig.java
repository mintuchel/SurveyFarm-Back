package notblank.surveyfarm.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    // Redis Client 인 Jeddis 랑 Lettuce 중 Lettuce 사용

    @Value("${spring.redis.host}")
    private String host;

    @Value(("${spring.redis.port}"))
    private int port;

    // Redis에 연결할 수 있는 객체를 생성할 수 있게 해주는 팩토리 객체를 Bean 으로 등록해줌
    // Lettuce 는 Redis Client임
    // 즉 host와 port에 연결할 Lettuce 객체를 생성할 수 있게 해주는 Factory 를 Bean 으로 등록해줌
    @Bean
    LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    // RedisTemplate
    // 템플릿 클래스로 데이터 조작을 추상화하고 자동화해 주는 역할을 수행하여 코드의 중복을 줄여주는 클래스
    // 기본적으로 데이터를 읽고 쓰는데 필요한 모든 작업을 처리해줌

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        return template;
    }
}
