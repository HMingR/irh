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
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.exception.NeedLoginException;
import top.imuster.common.core.utils.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

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
        String token = StringUtils.substringAfter(request.getHeader(HttpHeaders.AUTHORIZATION), GlobalConstant.JWT_TOKEN_HEAD);
        validate(token, handler);
        log.info("拦截器中获得的token为---->", token);
        UserDto loginUser = (UserDto)redisTemplate.opsForValue().get(RedisUtil.getAccessToken(token));
        if(null == loginUser){
            log.error("根据用户的token获得用户信息失败,需要重新登录");
            throw new NeedLoginException("根据用户的token获得用户信息失败,需要重新登录");
        }
        return true;
    }

    /**
     * @Description: 校验是否有@NeedLogin注解，如果有，则判断token是否为空
     * @Author: hmr
     * @Date: 2019/12/21 16:33
     * @param
     * @reture: boolean
     **/
    private void validate(String token, Object handler)throws NeedLoginException{
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        NeedLogin annotation = method.getAnnotation(NeedLogin.class);
        if(null == annotation || !annotation.validate()) {
            return;
        }
        if(StringUtils.isEmpty(token)) {
            throw new NeedLoginException("请先登录");
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
