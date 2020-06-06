package top.imuster.common.core.config;

import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName: JedisConfig
 * @Description: JedisConfig
 * @author: hmr
 * @date: 2020/5/24 15:07
 */
@Component
public class JedisConfig  {

    public RedisCommands commands;


    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Bean
    public RedisCommands getJedis() throws Exception {
        if(commands == null){
            RedisConnection connection = lettuceConnectionFactory.getConnection();
            RedisAsyncCommands redisAsyncCommands = (RedisAsyncCommands) connection.getNativeConnection();
            RedisCommands sync = redisAsyncCommands.getStatefulConnection().sync();
            return sync;
        }
        return commands;
    }
}
