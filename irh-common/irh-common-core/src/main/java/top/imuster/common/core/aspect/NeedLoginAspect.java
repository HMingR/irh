package top.imuster.common.core.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.exception.NeedLoginException;
import top.imuster.common.core.utils.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Resource
    RedisTemplate<String, Object> redisTemplate;


    @Pointcut("@annotation(top.imuster.common.core.annotation.NeedLogin)")
    private void pointCut(){}


    @Before("pointCut()")
    public void before(JoinPoint joinPoint) throws Exception{
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
        Class<?> clazz = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Method method = clazz.getMethod(methodName);
        NeedLogin annotation = method.getAnnotation(NeedLogin.class);
        if(null != annotation && annotation.validate())
            return true;
        return false;
    }

}
