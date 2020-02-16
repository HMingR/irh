package top.imuster.life.provider.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: test
 * @Description: TODO
 * @author: hmr
 * @date: 2020/2/16 13:45
 */
@RestController
@RequestMapping("/test")
public class test {

    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping
    public void test(){
        redisTemplate.opsForHash().put("asdf", "test", 1);
        redisTemplate.expire("asdf", 1, TimeUnit.MINUTES);
        Object o = redisTemplate.opsForHash().get("asdf", "test");
        System.out.println("获得的值为" + o);
        Long delete = redisTemplate.opsForHash().delete("asdf", "test");
        System.out.println(delete);
        Object o1 = redisTemplate.opsForHash().get("asdf", "test");
        System.out.println("删除之后再获取" + o1);
    }
}
