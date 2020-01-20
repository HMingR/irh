package top.imuster.common.core.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.imuster.common.core.annotation.LogAnnotation;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.exception.NeedLoginException;
import top.imuster.common.core.utils.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


/**
 * @ClassName: NeedLoginAspect
 * @Description: 校验被@NeedLogin注解表示的方法在访问的时候是否已经登录
 * @author: hmr
 * @date: 2019/12/20 20:08
 */

@Aspect
@Slf4j
@Component
public class NeedLoginAspect {

    @Value("enable.needLogin")
    private static boolean enable;

    @Resource
    RedisTemplate redisTemplate;

    @Pointcut("@annotation(top.imuster.common.core.annotation.NeedLogin)")
    private void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) throws Exception{
        if(!enable){
            log.info("由于关闭了NeedLogin注解功能，取消检测");
            return;
        }
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        log.info("进入校验切面, 是否需要校验{}", needValidate(joinPoint));
        String token = StringUtils.substringAfter(request.getHeader(HttpHeaders.AUTHORIZATION), "Bearer ");

        if(!needValidate(joinPoint))
            return;

        if(StringUtils.isEmpty(token)){
            throw new NeedLoginException("需要登录");
        }

        UserDto redisUser = (UserDto) redisTemplate.opsForValue().get(RedisUtil.getAccessToken(token));
        log.info("redis中获得的对象是:{}", new ObjectMapper().writeValueAsString(redisUser));
        if(null == redisUser)
            throw new NeedLoginException("用户没有登录或者登录超时,请重新登录");
    }


    private boolean needValidate(JoinPoint joinPoint) throws NoSuchMethodException {
        Annotation[] declaredAnnotations = joinPoint.getTarget().getClass().getDeclaredAnnotations();
        for (Annotation annotation : declaredAnnotations) {
            if(annotation instanceof NeedLogin){
                if(((NeedLogin) annotation).validate()){
                    return true;
                }
            }
        }
        return false;
    }

}
