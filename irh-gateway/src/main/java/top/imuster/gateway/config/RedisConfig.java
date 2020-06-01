package top.imuster.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @ClassName: RedisConfig
 * @Description: redis的配置类
 * @author: hmr
 * @date: 2020/2/1 16:49
 */
@Configuration
public class RedisConfig {

    /**
     * 自定义springSessionDefaultRedisSerializer对象，将会替代默认的SESSION序列化对象。
     * 默认是JdkSerializationRedisSerializer，缺点是需要类实现Serializable接口。
     * 并且在反序列化时如果异常会抛出SerializationException异常，
     * 而SessionRepositoryFilter又没有处理异常，故如果序列化异常时就会导致请求异常
     */
    @Bean(name = "springSessionDefaultRedisSerializer")
    public GenericJackson2JsonRedisSerializer getGenericJackson2JsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    /**
     * JacksonJsonRedisSerializer和GenericJackson2JsonRedisSerializer的区别：
     * GenericJackson2JsonRedisSerializer在json中加入@class属性，类的全路径包名，方便反系列化。
     * JacksonJsonRedisSerializer如果存放了List则在反系列化的时候，
     * 如果没指定TypeReference则会报错java.util.LinkedHashMap cannot be cast。
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        redisTemplate.setConnectionFactory(connectionFactory);
        // 配置默认的序列化器
        redisTemplate.setDefaultSerializer(getGenericJackson2JsonRedisSerializer());
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 设置 Key 的默认序列化机制
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer(Object.class);

        /*jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.afterPropertiesSet();*/
        return redisTemplate;
    }
}
