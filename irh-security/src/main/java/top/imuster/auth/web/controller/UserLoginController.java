package top.imuster.auth.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.imuster.auth.service.UserLoginService;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.utils.CookieUtil;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.security.api.bo.AuthToken;
import top.imuster.security.api.bo.SecurityUserDto;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName: UserLoginController
 * @Description: 所有用户登录的controller
 * @author: hmr
 * @date: 2020/1/27 17:23
 */
@RestController
@RequestMapping("/")
public class UserLoginController extends BaseController {

    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    @Autowired
    UserLoginService userLoginService;

    @ApiOperation(value = "会员登录，成功返回token", httpMethod = "POST")
    @PostMapping("login")
    public Message<SecurityUserDto> login(@Validated(ValidateGroup.loginGroup.class) @RequestBody UserInfo userInfo, BindingResult bindingResult) throws JsonProcessingException {
        validData(bindingResult);
        SecurityUserDto result = userLoginService.login(userInfo.getEmail(), userInfo.getPassword());
        saveAccessTokenToCookie(result.getAuthToken().getAccessToken());
        result.getAuthToken().setJwtToken(" ");
        result.getAuthToken().setRefreshToken(" ");
        return Message.createBySuccess(result);
    }

    @ApiOperation("在用户需要访问受保护的信息时，需要调用该接口获得jwt")
    @GetMapping("jwt")
    public Message<AuthToken> getUserJwt() throws IOException {
        String accessToken = getAccessTokenFromCookie();
        if(StringUtils.isBlank(accessToken)){
            return Message.createByError("登录过期,请重新登录");
        }
        AuthToken authToken = userLoginService.getUserAuthTokenByAccessToken(accessToken);
        return Message.createBySuccess(authToken);

    }


    @ApiOperation("用户退出登录")
    @GetMapping("logout")
    public Message<String> logout(){
        String accessToken = getAccessTokenFromCookie();
        deleteAccessTokenFromCookie(accessToken);
        userLoginService.deleteAccessTokenFromRedis(accessToken);
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 将用户申请到的令牌保存到cookie中
     * @Date: 2020/1/29 16:08
     * @param accessToken
     * @reture: void
     **/
    private void saveAccessTokenToCookie(String accessToken){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response, cookieDomain, "/", GlobalConstant.COOKIE_ACCESS_TOKEN_NAME, accessToken, cookieMaxAge, false);
    }

    /**
     * @Author hmr
     * @Description 根据名字从cookies中删除相应的cookie
     * @Date: 2020/1/29 16:10
     * @param cookieName
     * @reture: void
     **/
    private void deleteAccessTokenFromCookie(String accessToken){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response, cookieDomain, "/", GlobalConstant.COOKIE_ACCESS_TOKEN_NAME, accessToken, 0, false);
    }


    /**
     * @Author hmr
     * @Description 从cookie中获得accessToken
     * @Date: 2020/1/29 16:15
     * @param
     * @reture: java.lang.String
     **/
    private String getAccessTokenFromCookie(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> map = CookieUtil.readCookie(request, GlobalConstant.COOKIE_ACCESS_TOKEN_NAME);
        if(map!=null && map.get(GlobalConstant.COOKIE_ACCESS_TOKEN_NAME)!=null){
            return map.get(GlobalConstant.COOKIE_ACCESS_TOKEN_NAME);
        }
        return null;
    }
}
