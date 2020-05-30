package top.imuster.auth.component.login;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import top.imuster.auth.config.SmsCodeAuthenticationToken;
import top.imuster.common.core.utils.RedisUtil;

/**
 * @ClassName: IrhAuthenticationProvider
 * @Description: IrhAuthenticationProvider
 * @author: hmr
 * @date: 2020/4/30 14:36
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        //登录名
        String loginName = authenticationToken.getPrincipal() == null?"NONE_PROVIDED":authentication.getName();
        //验证码
        String verify = (String)authenticationToken.getCredentials();
        String redisCode = (String)redisTemplate.opsForValue().get(RedisUtil.getConsumerLoginByEmail(loginName));
        if(StringUtils.isEmpty(redisCode) || !verify.equalsIgnoreCase(redisCode)){
            throw new AuthenticationServiceException("验证码失效或者错误");
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (SmsCodeAuthenticationToken.class.isAssignableFrom(aClass));
    }

}
