package top.imuster.common.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.imuster.common.core.annotation.HotTopicAnnotation;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.utils.AspectUtil;
import top.imuster.common.core.utils.RedisUtil;

import java.util.concurrent.TimeUnit;

/**
 * 对HotTopicAnnotation进行拦截
 */
@Aspect
@Component
public class HotTopicAspect {

    RedisTemplate redisTemplate;

    @Autowired
    public HotTopicAspect(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Pointcut("@annotation(top.imuster.common.core.annotation.HotTopicAnnotation)")
    public void pointCut(){}

    @After("pointCut()")
    public void after(JoinPoint joinPoint){
        HotTopicAnnotation annotation = getAnnotation(joinPoint);
        BrowserType browserType = annotation.browserType();
        Long targetId = AspectUtil.getTargetId(joinPoint);
        String zSetKey = RedisUtil.getHotTopicKey(browserType);
        String key = new StringBuffer().append(zSetKey).append("::").append(targetId).toString();
        redisTemplate.expire(key, 35L, TimeUnit.MINUTES);
        redisTemplate.opsForZSet().incrementScore(zSetKey, key, 1);
    }

    /**
     * 获得注解
     * @param joinPoint
     * @return
     */
    private HotTopicAnnotation getAnnotation(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod().getAnnotation(HotTopicAnnotation.class);
    }
}
