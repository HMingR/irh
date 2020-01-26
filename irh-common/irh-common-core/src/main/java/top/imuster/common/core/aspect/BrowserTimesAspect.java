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
import org.springframework.web.bind.annotation.PathVariable;
import top.imuster.common.core.annotation.BrowserTimesAnnotation;
import top.imuster.common.core.enums.BrowserType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

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
            Long targetId = getTargetId(joinPoint);
            redisTemplate.opsForValue().increment(browserType.getRedisKeyHeader()+ String.valueOf(targetId), 1);
        }
    }

    /**
     * @Author hmr
     * @Description 从注解方法中获得需要查询的id
     * @Date: 2020/1/26 15:49
     * @param joinPoint
     * @reture: java.lang.Long
     **/
    private Long getTargetId(JoinPoint joinPoint){
        Long result;
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        //这个参数中保存的是参数的值
        Object[] params = joinPoint.getArgs();
        if(params.length == 0){
            log.error("{}方法的参数为0个", method.getName());
            return null;
        }

        //这个里面保存的是参数的类型信息(字节码信息)
        Parameter[] parameters = method.getParameters();

        for(int i = 0; i < parameterAnnotations.length; i++){
            Object param = params[i];
            Annotation[] paramAnn = parameterAnnotations[i];
            if(param == null || paramAnn.length == 0){
                continue;
            }
            for (Annotation annotation : paramAnn){
                if(annotation.annotationType().equals(PathVariable.class)){
                    Parameter parameter = parameters[i];
                    System.out.println(parameter.getType().toString());
                    if(parameter.getType().toString().contains("java.lang.Long")){
                        result = (Long) params[i];
                        System.out.println("结果为" + result);
                        return result;
                    }
                }
            }
        }
        return null;
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
