package top.imuster.common.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.utils.CookieUtil;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.exception.GlobalException;
import top.imuster.common.core.exception.NeedLoginException;
import top.imuster.common.core.utils.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName: BaseController
 * @Description: controller层一些共有的
 * @author: hmr
 * @date: 2019/12/1 10:36
 */
public class BaseController {

    @Value("${enable.needLogin}")
    private boolean enable;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

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
     * @Author hmr
     * @Description 从redis中获得当前用户的基本信息
     * @Date: 2020/2/1 17:39
     * @param
     * @reture: top.imuster.common.base.domain.BaseDomain
     **/
    protected UserDto getCurrentUserFromCookie(){
        String accessToken = getAccessTokenFromCookie();
        String jsonStr = (String) redisTemplate.opsForValue().get(RedisUtil.getAccessToken(accessToken));
        if(StringUtils.isBlank(jsonStr)) throw new NeedLoginException("用户身份过期,请重新登录后再操作");
        UserDto userDto = null;
        try {
            userDto = objectMapper.readValue(jsonStr, UserDto.class);
        } catch (IOException e) {
            throw new NeedLoginException("用户身份过期,请重新登录后再操作");
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
     * @Description 获得当前用户的id，如果require为true则必须登录，反之如果没查到则返回null
     * @Date: 2020/3/15 9:21
     * @param require
     * @reture: java.lang.Long
     **/
    protected Long getCurrentUserIdFromCookie(boolean require){
        Long userId;
        if(require){
            return getCurrentUserIdFromCookie();
        }else{
            try{
                userId = getCurrentUserIdFromCookie();
            }catch (Exception e){
                return null;
            }
            return userId;
        }
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

        //为了适配wx小程序,微信小程序不能设置cookie
        String header = request.getHeader(GlobalConstant.COOKIE_ACCESS_TOKEN_NAME);
        if(header != null){
            return header;
        }
        return null;
    }
}
