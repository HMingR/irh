package top.imuster.common.core.cache;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.Nullable;
import top.imuster.common.base.config.GlobalConstant;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: CustomizedRedisCacheManager
 * @Description: CustomizedRedisCacheManager
 * @author: hmr
 * @date: 2020/6/14 10:43
 */
public class CustomizedRedisCacheManager extends RedisCacheManager {
    private final RedisCacheWriter cacheWriter;
    private final RedisCacheConfiguration defaultCacheConfig;
    private final Map<String, RedisCacheConfiguration> initialCaches = new LinkedHashMap<>();
    private boolean enableTransactions;


    public CustomizedRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
    }

    public CustomizedRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
    }

    public CustomizedRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, boolean allowInFlightCacheCreation, String... initialCacheNames) {
        super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation, initialCacheNames);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
    }

    public CustomizedRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
    }

    public CustomizedRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;
    }

    /**
     * 这个构造方法最重要
     **/
    public CustomizedRedisCacheManager(RedisConnectionFactory redisConnectionFactory, RedisCacheConfiguration cacheConfiguration) {
        this(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),cacheConfiguration);
    }

    //覆盖父类创建RedisCache
    @Override
    protected RedisCache createRedisCache(String name, @Nullable RedisCacheConfiguration cacheConfig) {
        return new CustomizedRedisCache(name, cacheWriter, cacheConfig != null ? cacheConfig : defaultCacheConfig);
    }

    @Override
    public Map<String, RedisCacheConfiguration> getCacheConfigurations() {

        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put(GlobalConstant.IRH_COMMON_CACHE_KEY, defaultCacheConfig.entryTtl(Duration.ofMinutes(2L)));
        configMap.put(GlobalConstant.IRH_HOT_TOPIC_CACHE_KEY, defaultCacheConfig.entryTtl(Duration.ofMinutes(30L)));
        configMap.put(GlobalConstant.IRH_USER_INFO_CACHE_KEY, defaultCacheConfig.entryTtl(Duration.ofMinutes(30L)));
        configMap.put(GlobalConstant.IRH_FIVE_MINUTES_CACHE_KEY, defaultCacheConfig.entryTtl(Duration.ofMinutes(5L)));
        configMap.put(GlobalConstant.IRH_TEN_MINUTES_CACHE_KEY, defaultCacheConfig.entryTtl(Duration.ofMinutes(10L)));
        configMap.put(GlobalConstant.IRH_THIRTY_MINUTES_CACHE_KEY, defaultCacheConfig.entryTtl(Duration.ofMinutes(30L)));

        /*Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>(getCacheNames().size());
        getCacheNames().forEach(it -> {
            RedisCache cache = CustomizedRedisCache.class.cast(lookupCache(it));
            configurationMap.put(it, cache != null ? cache.getCacheConfiguration() : null);
        });*/
        return Collections.unmodifiableMap(configMap);
    }
}
