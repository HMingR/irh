package top.imuster.auth.web.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.imuster.auth.service.UserLoginService;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.utils.CookieUtil;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.Idempotent;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.security.api.bo.AuthToken;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

    @Resource
    UserLoginService userLoginService;

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @ApiOperation("在用户需要访问受保护的信息时，需要调用该接口获得jwt")
    @NeedLogin
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
    @NeedLogin
    @GetMapping("logout")
    public Message<String> logout(){
        String accessToken = getAccessTokenFromCookie();
        deleteAccessTokenFromCookie(accessToken);
        userLoginService.deleteAccessTokenFromRedis(accessToken);
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 发送给web端的验证码
     * @Date: 2020/5/16 10:45
     * @param email
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @GetMapping("/sendCode/{email}")
    @Idempotent(submitTotal = 4, timeTotal = 5, timeUnit = TimeUnit.MINUTES)
    public Message<String> getCode(@PathVariable("email") String email){
        return userLoginService.getWebCodeByEmail(email);
    }


    /**
     * @Author hmr
     * @Description 发送email验证码
     * @Date: 2020/4/30 10:12
     * @param email  接受code的邮箱
     * @param type   1-注册  2-登录  3-忘记密码
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation(value = "发送email验证码",httpMethod = "GET")
    @Idempotent(submitTotal = 5, timeTotal = 30, timeUnit = TimeUnit.MINUTES)
    @GetMapping("/sendCode/{type}/{email}")
    public Message<String> getCode(@ApiParam("邮箱地址") @PathVariable("email") String email, @PathVariable("type") Integer type) throws Exception {
        if(type != 1 && type != 2 && type != 3 && type != 4){
            return Message.createByError("参数异常,请刷新后重试");
        }
        userLoginService.getCode(email, type);
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
     * @param accessToken
     * @reture: void
     **/
    private void deleteAccessTokenFromCookie(String accessToken){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response, cookieDomain, "/", GlobalConstant.COOKIE_ACCESS_TOKEN_NAME, accessToken, 0, false);
    }

}
