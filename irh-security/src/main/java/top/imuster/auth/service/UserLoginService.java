package top.imuster.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.utils.JwtTokenUtil;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.security.api.bo.UserDetails;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.UserServiceFeignApi;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: UserLoginService
 * @Description: UserLoginService
 * @author: hmr
 * @date: 2020/1/27 16:47
 */
@Service("userLoginService")
@Slf4j
public class UserLoginService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    public UserDetails getUserDetailsByName(String name){
        UserInfo userInfo = userServiceFeignApi.loadUserInfoByEmail(name);
        return new UserDetails(userInfo);
    }

    public UserInfo userLogin(String email, String password){
        UserInfo condition = new UserInfo();
        condition.setEmail(email);
        UserInfo userInfo = getUserDetailsByName(email).getUserInfo();
        boolean matches = passwordEncoder.matches(password, userInfo.getPassword());
        if(!matches){
            throw new SecurityException("用户名或者密码错误");
        }
        if(StringUtils.isEmpty(String.valueOf(userInfo.getState())) || userInfo.getState() <= 20){
            throw new SecurityException("用户信息异常或用户被锁定");
        }
        String token = JwtTokenUtil.generateToken(userInfo.getEmail(), userInfo.getId());
        //将用户的基本信息存入redis中，并设置过期时间
        redisTemplate.opsForValue()
                .set(RedisUtil.getAccessToken(token),
                        new UserDto(userInfo.getId(),
                                userInfo.getEmail(),
                                GlobalConstant.userType.MANAGEMENT.getName(),
                                GlobalConstant.userType.MANAGEMENT.getType()),
                        GlobalConstant.REDIS_JWT_EXPIRATION, TimeUnit.SECONDS);
        userInfo.setToken(GlobalConstant.JWT_TOKEN_HEAD + token);
        return userInfo;
    }

}
