package top.imuster.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.imuster.utils.CusThreadLocal;
import top.imuster.dto.UserDto;
import top.imuster.enums.GlobalConstant;
import top.imuster.utils.RedisUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: TokenInterceptor
 * @Description: 用户token的拦截器
 * @author: hmr
 * @date: 2019/12/15 15:21
 */
@Component("tokenInterceptor")
public class TokenInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

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
        String token = StringUtils.substringAfter(request.getHeader(HttpHeaders.AUTHORIZATION), "Bearer ");
        logger.info("获得token为:{}", token);
        UserDto loginUser = (UserDto)redisTemplate.opsForValue().get(RedisUtil.getAccessToken(token));
        if(null == loginUser){
            logger.error("根据用户的token获得用户信息失败,需要重新登录");
            return false;
        }
        CusThreadLocal.put(GlobalConstant.USER_TOKEN_DTO.getValue(), loginUser);
        return true;
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
        res.getWriter().write("{\"code\":500 ,\"text\" :\"解析token失败\"}");
        res.flushBuffer();
    }

}
