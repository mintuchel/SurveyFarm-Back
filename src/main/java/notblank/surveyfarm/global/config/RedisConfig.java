package notblank.surveyfarm.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

//// 인메모리 DB 인 Redis 를 위한 Configuration 클래스
//@Configuration
//public class RedisConfig {
//
//    // Redis Client 인 Jeddis 랑 Lettuce 중 Lettuce 사용
//    @Value("${spring.redis.host}")
//    private String host;
//
//    @Value(("${spring.redis.port}"))
//    private int port;
//
//    // RedisConnectionFactory = Redis 서버와의 연결을 관리하는 객체
//    // Redis에 연결할 수 있는 객체를 생성할 수 있게 해주는 팩토리 객체를 Bean 으로 등록해줌
//    // Lettuce 는 Redis Client임
//    // 즉 host와 port에 연결할 Lettuce 객체를 생성할 수 있게 해주는 Factory 를 Bean 으로 등록해줌
//    @Bean
//    LettuceConnectionFactory connectionFactory() {
//        return new LettuceConnectionFactory(host, port);
//    }
//
//    // RedisTemplate
//    // 템플릿 클래스로 데이터 조작을 추상화하고 자동화해 주는 역할을 수행하여 코드의 중복을 줄여주는 클래스
//    // 기본적으로 데이터를 읽고 쓰는데 필요한 모든 작업을 처리해줌
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory());
//        return template;
//    }
//
//    // cacheManager Bean 등록
//    // 캐시 저장소로 Redis를 사용하자
//    // RedisCacheManager 설정 (캐시 저장소로 Redis 사용)
//    // CacheManager의 구현체가 필요하니 RedisCacheManager를 반환하게 하자
//    // @Bean
//    public CacheManager cacheManager(RedisConnectionFactory factory) {
//
//        // Cache 설정정보 구성하기
//
//        // serializeKeysWith, serializeValuesWith 는 Redis에서 캐시를 사용할 때 key와 value의 직렬화 방식을 설정하는 부분임
//        // 캐시의 key와 value가 어떻게 저장될지를 결정하며, 저장할 때 객체를 직렬화하고 불러올 때는 역직렬화하는 방식을 정의하는 부분
//        // Time To Live = 캐시항목의 만료시간 (하루)
//        // TTL 이 만료되면 자동으로 캐시에서 삭제됨
//
//        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
//                // Redis <-> Cache (key 값은 String 형식으로 직렬화/역직렬화)
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                // Redis <-> Cache (value 값은 Json 형식으로 직렬화/역직렬화)
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
//                .entryTtl(Duration.ofDays(1));
//
//        return RedisCacheManager
//                .RedisCacheManagerBuilder
//                // RedisCacheManager 는 초기화하려면 RedisConnectionFactory 가 필요함
//                // 여기서는 위에서 Bean 으로 생성해둔 LettuceConnectionFactory 가 주입되는거임
//                .fromConnectionFactory(factory)
//                // cache 설정을 앞서 정의한 cacheConfig 로 하겠다 라는 의미
//                .cacheDefaults(cacheConfig)
//                .build();
//    }
//}
