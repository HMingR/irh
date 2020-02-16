package top.imuster.common.core.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.base.utils.CookieUtil;
import top.imuster.common.base.utils.JwtTokenUtil;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.exception.GlobalException;
import top.imuster.common.core.utils.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.Map;

/**
 * @ClassName: BaseController
 * @Description: controller层一些共有的
 * @author: hmr
 * @date: 2019/12/1 10:36
 */
public class BaseController {

    @Autowired
    RedisTemplate redisTemplate;

    protected  final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void validData(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer sb = new StringBuffer();
            for (ObjectError error : bindingResult.getAllErrors()) {
                sb.append(error.getDefaultMessage());
            }
            throw new ValidationException(sb.toString());
        }
    }

    /**
     * @Description: 从token中解析当前用户的id
     * @Author: hmr
     * @Date: 2020/1/10 16:00
     * @param request
     * @reture: java.lang.Long
     **/
    protected Long getIdByToken(HttpServletRequest request)throws Exception{
        String token = StringUtils.substringAfter(request.getHeader(GlobalConstant.JWT_TOKEN_HEADER), GlobalConstant.JWT_TOKEN_HEAD);
        return JwtTokenUtil.getUserIdFromToken(token);
    }


    /**
     * @Author hmr
     * @Description 从redis中获得当前用户的基本信息
     * @Date: 2020/2/1 17:39
     * @param
     * @reture: top.imuster.common.base.domain.BaseDomain
     **/
    protected UserDto getCurrentUserFromCookie(){
        String accessToken = getAccessTokenFromCookie();
        UserDto userDto = (UserDto) redisTemplate.opsForValue().get(RedisUtil.getAccessToken(accessToken));
        if(userDto == null){
            throw new RuntimeException("用户身份货过期,请重新登录后再操作");
        }
        return userDto;
    }

    /**
     * @Author hmr
     * @Description 从cookie中获得id
     * @Date: 2020/2/1 19:53
     * @param
     * @reture: java.lang.Long
     **/
    protected Long getCurrentUserIdFromCookie(){
        UserDto currentUser = getCurrentUserFromCookie();
        if(currentUser == null || currentUser.getUserId() == null){
            throw new GlobalException("用户身份过期,请重新登录");
        }
        return currentUser.getUserId();

    }

    /**
     * @Author hmr
     * @Description 从cookie中获得accessToken
     * @Date: 2020/1/29 16:15
     * @param
     * @reture: java.lang.String
     **/
    protected String getAccessTokenFromCookie(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> map = CookieUtil.readCookie(request, GlobalConstant.COOKIE_ACCESS_TOKEN_NAME);
        if(map!=null && map.get(GlobalConstant.COOKIE_ACCESS_TOKEN_NAME)!=null){
            return map.get(GlobalConstant.COOKIE_ACCESS_TOKEN_NAME);
        }
        return null;
    }


}
