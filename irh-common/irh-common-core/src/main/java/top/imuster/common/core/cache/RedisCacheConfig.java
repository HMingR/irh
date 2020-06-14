package top.imuster.common.core.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @ClassName: RedisCacheConfig
 * @Description: 使用redis作为缓存工具
 * @author: hmr
 * @date: 2020/2/6 16:34
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    /**
     * @Author hmr
     * @Description 添加自定义缓存异常处理   当缓存读写异常时,忽略异常
     * @Date: 2020/2/6 17:26
     * @param
     * @reture: org.springframework.cache.interceptor.CacheErrorHandler
     **/
    @Override
    public CacheErrorHandler errorHandler() {
        return new IgnoreExceptionCacheErrorHandler();
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        ObjectMapper objectMapper = new ObjectMapper();
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))
                .disableCachingNullValues()
                .computePrefixWith(cacheName -> "irh::cache".concat(":").concat(cacheName).concat(":"))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(createJackson2JsonRedisSerializer(objectMapper)));

        /*RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();  // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        config = config.entryTtl(Duration.ofMinutes(2))     // 设置缓存的默认过期时间，也是使用Duration设置
                .disableCachingNullValues();     // 不缓存空值

        // 设置一个初始化的缓存空间set集合
        Set<String> cacheNames =  new HashSet<>();
        cacheNames.add(GlobalConstant.IRH_COMMON_CACHE_KEY);
        cacheNames.add(GlobalConstant.IRH_HOT_TOPIC_CACHE_KEY);


        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put(GlobalConstant.IRH_COMMON_CACHE_KEY, config);
        configMap.put(GlobalConstant.IRH_HOT_TOPIC_CACHE_KEY, config.entryTtl(Duration.ofMinutes(30L)));
        configMap.put(GlobalConstant.IRH_USER_INFO_CACHE_KEY, config.entryTtl(Duration.ofMinutes(30L)));
        configMap.put(GlobalConstant.IRH_FIVE_MINUTES_CACHE_KEY, config.entryTtl(Duration.ofMinutes(5L)));
        configMap.put(GlobalConstant.IRH_TEN_MINUTES_CACHE_KEY, config.entryTtl(Duration.ofMinutes(10L)));
        configMap.put(GlobalConstant.IRH_THIRTY_MINUTES_CACHE_KEY, config.entryTtl(Duration.ofMinutes(30L)));
*/
        /*RedisCacheManager cacheManager = RedisCacheManager.builder(factory)     // 使用自定义的缓存配置初始化一个cacheManager
                .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .withInitialCacheConfigurations(configMap)
                .build();*/
        return new CustomizedRedisCacheManager(factory, cacheConfiguration);
//        return cacheManager;
    }


    private RedisSerializer<Object> createJackson2JsonRedisSerializer(ObjectMapper objectMapper) {
        // TODO Auto-generated method stub\
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    /**
     * @Author hmr
     * @Description 自定义缓存key生成策略
     * @Date: 2020/2/6 16:36
     * @param
     * @reture: org.springframework.cache.interceptor.KeyGenerator
     **/
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator(){
            @Override
            public Object generate(Object target, java.lang.reflect.Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for(Object obj:params){
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
}
