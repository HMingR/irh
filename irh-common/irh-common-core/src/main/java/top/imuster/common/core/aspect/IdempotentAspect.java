package top.imuster.common.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.imuster.common.core.annotation.Idempotent;
import top.imuster.common.core.exception.BusyOperationException;
import top.imuster.common.core.utils.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: IdempotentAspect
 * @Description: IdempotentAspect
 * @author: hmr
 * @date: 2020/5/20 14:49
 */
@Aspect
@Component
public class IdempotentAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate redisTemplate;

    @Pointcut("@annotation(top.imuster.common.core.annotation.Idempotent)")
    private void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        Idempotent annotation = getAnnotation(joinPoint);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String remoteAddr = RequestUtil.getRemoteAddr(request);
        String methodName = getMethodName(joinPoint);
        String redisKey = new StringBuffer(methodName).append("::").append(remoteAddr).toString();

        int submitTotal = annotation.submitTotal();
        int timeTotal = annotation.timeTotal();
        TimeUnit timeUnit = annotation.timeUnit();

        Object o = redisTemplate.opsForValue().get(redisKey);
        if(o == null){
            redisTemplate.opsForValue().set(redisKey, submitTotal - 1, timeTotal, timeUnit);
        }else{
            int i = Integer.parseInt(String.valueOf(o));
            if(i == 0){
                String msg = annotation.msg();
                throw new BusyOperationException(msg);
            }
            redisTemplate.opsForValue().increment(redisKey, -1);
        }

    }


    /**
     * @Author hmr
     * @Description 从方法中获得参数信息
     * @Date: 2020/1/26 16:12
     * @param joinPoint
     * @reture: top.imuster.common.core.annotation.BrowserAnnotation
     **/
    private Idempotent getAnnotation(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod().getAnnotation(Idempotent.class);
    }

    /**
     * @Author hmr
     * @Description 获得方法的完整路径
     * @Date: 2020/5/20 19:31
     * @param joinPoint
     * @reture: java.lang.String
     **/
    private String getMethodName(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String clazzName = methodSignature.getMethod().getDeclaringClass().getName();
        String methodName = methodSignature.getMethod().getName();
        return new StringBuffer(clazzName).append(methodName).toString();
    }
}
