package top.imuster.auth.component;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.imuster.auth.config.IrhAuthenticationToken;
import top.imuster.auth.config.PasswordCodeAuthenticationToken;
import top.imuster.common.core.utils.RedisUtil;

import javax.annotation.Resource;

/**
 * @ClassName: IrhAuthenticationProvider
 * @Description: 用户名密码登录
 * @author: hmr
 * @date: 2020/4/30 14:36
 */
public class PasswordAuthenticationProvider implements AuthenticationProvider {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    UserDetailsService usernameUserDetailsService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PasswordCodeAuthenticationToken authenticationToken = (PasswordCodeAuthenticationToken) authentication;

        String webCode = authenticationToken.getWebCode();
        //登录名
        String loginName = authenticationToken.getPrincipal() == null?"NONE_PROVIDED":authenticationToken.getPrincipal().toString();

        String redisCode = (String) redisTemplate.opsForValue().get(RedisUtil.getWebCodeByEmail(loginName));

        if(StringUtils.isBlank(redisCode) || !webCode.equalsIgnoreCase(redisCode)) throw new AuthenticationServiceException("验证码错误");
        //密码
        String verify = (String)authenticationToken.getCredentials();
        UserDetails user = usernameUserDetailsService.loadUserByUsername(loginName);
        if(user == null) throw new AuthenticationServiceException("用户名不存在");
        if(!passwordEncoder.matches(verify, user.getPassword())) throw new AuthenticationServiceException("密码错误");

        IrhAuthenticationToken returnAuth = new IrhAuthenticationToken(user, verify);
        return returnAuth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass));
    }

}
