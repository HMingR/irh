package top.imuster.common.core.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @ClassName: RedisCacheConfig
 * @Description: 使用redis作为缓存工具
 * @author: hmr
 * @date: 2020/2/6 16:34
 */
@Configuration
@EnableCaching
@Slf4j
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

    //缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory).build();
        return cacheManager;
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
