package top.imuster.common.core.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.exception.NeedLoginException;
import top.imuster.common.core.utils.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: TokenInterceptor
 * @Description: 用户token的拦截器
 * @author: hmr
 * @date: 2019/12/15 15:21
 */
@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @Description: 在到达处理器之前
     * @Author: hmr
     * @Date: 2019/12/15 15:53
     * @param request
     * @param response
     * @param handler
     * @reture: boolean
     **/
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*String token = StringUtils.substringAfter(
                request.getHeader(HttpHeaders.AUTHORIZATION), GlobalConstant.JWT_TOKEN_HEAD);
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        NeedLogin annotation = method.getAnnotation(NeedLogin.class);
        //没有@NeedLogin注解的可以直接返回
        if(null == annotation || !annotation.validate()){
            return true;
        }
        //有@NeedLogin注解的
        if(StringUtils.isBlank(token)) {
            throw new NeedLoginException("请先登录");
        }
        if(!redisTemplate.hasKey(RedisUtil.getAccessToken(token))){
            log.error("根据用户的token获得用户信息失败,需要重新登录");
            throw new NeedLoginException("根据用户的token获得用户信息失败,需要重新登录");
        }
        refresh(token);*/
        return true;
    }

    /**
     * @Description: 刷新redis中的token的过期时间
     * @Author: hmr
     * @Date: 2019/12/21 18:17
     * @param
     * @reture: String
     **/
    private void refresh(String token) {
        try{
            //刷新redis的过期时间
            redisTemplate.expire(token, GlobalConstant.REDIS_JWT_EXPIRATION, TimeUnit.SECONDS);
            log.info("刷新redis中的token过期时间");
        }catch (Exception e){
            throw new RuntimeException("刷新token失败");
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        if(ex != null){
            this.handleException(response);
        }
    }

    /**
     * @Description: 当出现错误的时候，则直接返回json数据
     * @Author: hmr
     * @Date: 2019/12/15 16:32
     * @param res
     * @reture: void
     **/
    private void handleException(HttpServletResponse res) throws IOException {
        res.resetBuffer();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(new ObjectMapper().writeValueAsString(Message.createByError("登录超时或没有登录,请先登录")));
        res.flushBuffer();
    }

}
