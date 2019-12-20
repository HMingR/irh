package top.imuster.common.core.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.exception.NeedLoginException;
import top.imuster.common.core.utils.CusThreadLocal;
import top.imuster.common.core.utils.JwtTokenUtil;
import top.imuster.common.core.utils.RedisUtil;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;

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
        log.info("进入校验切面");
        if(!needValidate(joinPoint))
            return;

        UserDto loginUser = (UserDto) CusThreadLocal.get(GlobalConstant.USER_TOKEN_DTO);
        log.info("本地线程中国的的对象是:{}", new ObjectMapper().writeValueAsString(loginUser));
        if(null == loginUser)
            throw new NeedLoginException("用户没有登录或者登录超时,请重新登录");

        String token = JwtTokenUtil.generateToken(loginUser.getLoginName(), loginUser.getCreateTime());
        UserDto redisUser = (UserDto) redisTemplate.opsForValue().get(RedisUtil.getAccessToken(token));
        log.info("redis中获得的对象是:{}", new ObjectMapper().writeValueAsString(redisUser));
        if(null == redisUser)
            throw new NeedLoginException("用户没有登录或者登录超时,请重新登录");
    }


    private boolean needValidate(JoinPoint joinPoint){
        Annotation[] annotations = joinPoint.getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            if(annotation instanceof NeedLogin){
                if(((NeedLogin) annotation).validate()){
                    return true;
                }
            }
        }
        return false;
    }

}
