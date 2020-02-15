package top.imuster.common.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.core.annotation.BrowserTimesAnnotation;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.utils.AspectUtil;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: BrowserTimesAspect
 * @Description: BrowserTimes的拦截器，实现向reids中新增浏览次数的逻辑
 * @author: hmr
 * @date: 2020/1/23 17:22
 */
@Component
@Slf4j
@Aspect
public class BrowserTimesAspect  {

    @Autowired
    RedisTemplate redisTemplate;

    @Pointcut("@annotation(top.imuster.common.core.annotation.BrowserTimesAnnotation)")
    public void pointCut(){}

    @After("pointCut()")
    public void after(JoinPoint joinPoint){
        BrowserTimesAnnotation annotation = getAnnotation(joinPoint);
        Optional<BrowserTimesAnnotation> browserTimesAnnotation = Optional.ofNullable(annotation);
        if(browserTimesAnnotation.isPresent()){
            BrowserType browserType = annotation.browserType();
            Long targetId = AspectUtil.getTargetId(joinPoint);
            redisTemplate.expire(String.valueOf(targetId), 30L, TimeUnit.MINUTES);
            redisTemplate.opsForHash().increment(browserType.getRedisKeyHeader(), String.valueOf(targetId), 1);
        }
    }

    /**
     * @Author hmr
     * @Description 从方法中获得参数信息
     * @Date: 2020/1/26 16:12
     * @param joinPoint
     * @reture: top.imuster.common.core.annotation.BrowserTimesAnnotation
     **/
    private BrowserTimesAnnotation getAnnotation(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod().getAnnotation(BrowserTimesAnnotation.class);
    }

}
