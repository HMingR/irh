package top.imuster.common.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * @ClassName: JedisConfig
 * @Description: JedisConfig
 * @author: hmr
 * @date: 2020/5/24 15:07
 */
@Component
public class JedisConfig  {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public Jedis getJedis() throws Exception {
        RedisConnection connection = jedisConnectionFactory.getConnection();
        return (Jedis) connection.getNativeConnection();

    }
}
