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
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.exception.NeedLoginException;
import top.imuster.common.core.utils.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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

    @Value("enable.needLogin")
    private static boolean enable;

    @Resource
    RedisTemplate redisTemplate;

    @Pointcut("@annotation(top.imuster.common.core.annotation.NeedLogin)")
    private void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) throws Exception{
        if(!enable){
            log.info("测试模式,关闭了NeedLogin注解功能，取消检测");
            return;
        }
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie[] cookies = request.getCookies();
        String accessToken = null;
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(GlobalConstant.COOKIE_ACCESS_TOKEN_NAME)){
                accessToken = cookie.getValue();
                break;
            }
        }

        if(StringUtils.isBlank(accessToken)) throw new NeedLoginException("您暂时还没有登录,请登陆后重试");

        Boolean hasKey = redisTemplate.hasKey(RedisUtil.getAccessToken(accessToken));
        if(!hasKey) throw new NeedLoginException("您当前的会话已经超时,请重新登录");
    }

}
