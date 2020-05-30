package top.imuster.auth.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.imuster.auth.exception.CustomSecurityException;
import top.imuster.auth.service.UserLoginService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.rabbitMq.SendEmailDto;
import top.imuster.common.core.enums.TemplateEnum;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.security.api.bo.AuthToken;
import top.imuster.user.api.service.UserServiceFeignApi;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: UserLoginService
 * @Description: UserLoginService
 * @author: hmr
 * @date: 2020/1/27 16:47
 */
@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Autowired
    DiscoveryClient discoveryClient;


    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;

    public void getCode(String email, Integer type){
        SendEmailDto emailDto = new SendEmailDto();
        String code = UUID.randomUUID().toString().substring(0, 4);
        emailDto.setEmail(email);
        emailDto.setContent(code);
        log.info("验证码为:{}", code);
        if(type == 1){
            emailDto.setRedisKey(RedisUtil.getConsumerRegisterByEmailToken(email));
            emailDto.setSubject("irh注册");
            emailDto.setTemplateEnum(TemplateEnum.USER_REGISTER);
        }else if(type == 2){
            emailDto.setRedisKey(RedisUtil.getConsumerLoginByEmail(email));
            emailDto.setSubject("irh验证码登录");
            emailDto.setTemplateEnum(TemplateEnum.USER_LOGIN);
        }else if (type == 3){
            emailDto.setRedisKey(RedisUtil.getConsumerResetPwdKey(email));
            emailDto.setTemplateEnum(TemplateEnum.USER_RESETPWD);
            emailDto.setSubject("irh重置密码");
        }
        emailDto.setExpiration(20L);
        emailDto.setUnit(TimeUnit.MINUTES);
        generateSendMessageService.sendRegistEmail(emailDto);
    }

    /**
     * @Author hmr
     * @Description 从redis中获得存储的AuthToken对象
     * @Date: 2020/1/29 17:54
     * @param accessToken
     * @reture: top.imuster.security.api.bo.AuthToken
     **/
    public AuthToken getUserAuthTokenByAccessToken(String accessToken) throws IOException {
        String key = RedisUtil.getAccessToken(accessToken);
        String obj = (String) redisTemplate.opsForValue().get(key);
        AuthToken authToken = new ObjectMapper().readValue(obj, AuthToken.class);
        if(authToken == null){
            throw new CustomSecurityException("登录超时,请重新登录");
        }
        return authToken;
    }

    public void deleteAccessTokenFromRedis(String accessToken){
        redisTemplate.delete(RedisUtil.getAccessToken(accessToken));
    }

    @Override
    public Message<String> getWebCodeByEmail(String email) {
        String code = cn.hutool.core.lang.UUID.randomUUID().toString().substring(0, 4);
        redisTemplate.opsForValue().set(RedisUtil.getWebCodeByEmail(email), code, 5, TimeUnit.MINUTES);
        return Message.createBySuccess(code);
    }
}
