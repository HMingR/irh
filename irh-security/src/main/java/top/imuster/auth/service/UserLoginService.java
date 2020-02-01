package top.imuster.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import top.imuster.auth.exception.SecurityException;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.security.api.bo.AuthToken;
import top.imuster.security.api.bo.SecurityUserDto;
import top.imuster.user.api.service.UserServiceFeignApi;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
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

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;

    /**
     * @Author hmr
     * @Description 用户登录
     * @Date: 2020/1/29 14:58
     * @param
     * @reture: top.imuster.security.api.bo.SecurityUserDto
     **/
    public SecurityUserDto login(String loginName, String password) throws JsonProcessingException {
        AuthToken authToken = applyToken(loginName, password, clientId, clientSecret);
        String accessToken = authToken.getAccessToken();
        String content = new ObjectMapper().writeValueAsString(authToken);
        saveTokenToRedis(accessToken, content, tokenValiditySeconds);
        return new SecurityUserDto(authToken);
    }

    /**
     * @Author hmr
     * @Description 将申请到的令牌保存到redis中，其中以accessToken作为key，AuthToken作为内容
     * @Date: 2020/1/29 15:23
     * @param accessToken
     * @param content
     * @param ttl
     * @reture: boolean 查看是否存储成功  true标识存储成功  false标识存储失败
     **/
    private void saveTokenToRedis(String accessToken, String content, long ttl){
        String key = RedisUtil.getAccessToken(accessToken);
        redisTemplate.boundValueOps(key).set(content, ttl, TimeUnit.SECONDS);
        Long expire = redisTemplate.getExpire(key);
        if(expire < 0){
            log.error("用户登录的时候存储令牌到redis中失败");
            throw new SecurityException("令牌存储失败,请稍后重试");
        }
    }

    public void getCode(String email){
        SendMessageDto sendMessageDto = new SendMessageDto();
        String code = UUID.randomUUID().toString().substring(0, 4);
        sendMessageDto.setUnit(TimeUnit.MINUTES);
        sendMessageDto.setExpiration(10L);
        sendMessageDto.setValue(code);
        sendMessageDto.setType(MqTypeEnum.EMAIL);
        sendMessageDto.setRedisKey(RedisUtil.getConsumerRegisterByEmailToken(email));
        sendMessageDto.setTopic("注册");
        String body = new StringBuilder().append("欢迎注册,本次注册的验证码是").append(code).append(",该验证码有效期为10分钟").toString();
        sendMessageDto.setBody(body);
        generateSendMessageService.sendToMqAndReids(sendMessageDto);
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
            throw new SecurityException("登录超时,请重新登录");
        }
        return authToken;
    }

    /**
     * @Author hmr
     * @Description 向security申请令牌
     * @Date: 2020/1/29 15:04
     * @param loginName
     * @param password
     * @param clientId
     * @param clientSecret
     * @reture: top.imuster.security.api.bo.AuthToken
     **/
    private AuthToken applyToken(String loginName, String password, String clientId, String clientSecret){
        String authUrl =  "http://localhost:10000/oauth/token";
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String httpBasic = getHttpBasic(clientId, clientSecret);
        header.add("Authorization",httpBasic);

        //定义body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username",loginName);
        body.add("password",password);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);

        //设置restTemplate远程调用时候，对400和401不让报错，正确返回数据
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if(response.getRawStatusCode()!=400 && response.getRawStatusCode()!=401){
                    super.handleError(response);
                }
            }
        });

        ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);

        //申请令牌信息
        Map resultMap = exchange.getBody();

        if(resultMap == null || resultMap.get("access_token") == null || resultMap.get("refresh_token") == null || resultMap.get("jti") == null){
            //解析spring security返回的错误信息
            if(resultMap!=null && resultMap.get("error_description")!=null){
                String error_description = (String) resultMap.get("error_description");
                if(error_description.indexOf("UserDetailsService returned null")>=0){
                    throw new SecurityException("账号不存在,请检查后重试");
                }else if(error_description.indexOf("坏的凭证")>=0){
                    throw new SecurityException("账号或密码错误");
                }
            }
            throw new SecurityException("用户名或密码错误");
        }
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken((String) resultMap.get("jti"));//用户身份令牌
        authToken.setRefreshToken((String) resultMap.get("refresh_token"));//刷新令牌
        authToken.setJwtToken((String) resultMap.get("access_token"));//jwt令牌
        return authToken;
    }

    public void deleteAccessTokenFromRedis(String accessToken){
        redisTemplate.delete(RedisUtil.getAccessToken(accessToken));
    }


    /**
     * @Author hmr
     * @Description 获得Http Basic串
     * @Date: 2020/1/29 15:18
     * @param clientId
     * @param clientSecret
     * @reture: java.lang.String
     **/
    private String getHttpBasic(String clientId,String clientSecret){
        String string = clientId+":"+clientSecret;
        //将串进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic "+new String(encode);
    }

}
