package top.imuster.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import top.imuster.common.base.wrapper.Message;
import top.imuster.security.api.bo.AuthToken;
import top.imuster.security.api.bo.SecurityUserDto;
import top.imuster.security.api.dto.UserLoginDto;
import top.imuster.user.api.pojo.UserInfo;

import java.io.IOException;

/**
 * @ClassName: UserLoginService
 * @Description: UserLoginService
 * @author: hmr
 * @date: 2020/4/30 19:20
 */
public interface UserLoginService {

    /**
     * @Author hmr
     * @Description 用户登录
     * @Date: 2020/4/30 19:21
     * @param loginName 用户名
     * @param password 密码
     * @reture: top.imuster.security.api.bo.SecurityUserDto
     **/
    SecurityUserDto login(String loginName, String password) throws JsonProcessingException;


    /**
     * @Author hmr
     * @Description 根据验证码登录
     * @Date: 2020/4/30 19:23
     * @param userLoginDto
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.security.api.bo.SecurityUserDto>
     **/
    Message<SecurityUserDto> loginByCode(UserLoginDto userLoginDto) throws JsonProcessingException;

    /**
     * @Author hmr
     * @Description 发送邮箱验证码
     * @Date: 2020/4/30 19:21
     * @param email
     * @param type
     * @reture: void
     **/
    void getCode(String email, Integer type);

    /**
     * @Author hmr
     * @Description 根据accessToken获得用户的jwt
     * @Date: 2020/4/30 19:22
     * @param accessToken
     * @reture: top.imuster.security.api.bo.AuthToken
     **/
    AuthToken getUserAuthTokenByAccessToken(String accessToken) throws IOException;


    /**
     * @Author hmr
     * @Description 注册
     * @Date: 2020/4/30 19:23
     * @param userInfo
     * @param code
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> register(UserInfo userInfo, String code);

    /**
     * @Author hmr
     * @Description 用户退出
     * @Date: 2020/4/30 19:25
     * @param accessToken
     * @reture: void
     **/
    void deleteAccessTokenFromRedis(String accessToken);
}
