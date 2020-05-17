package top.imuster.auth.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import top.imuster.auth.config.PwdAuthenticationToken;
import top.imuster.common.core.utils.RedisUtil;

/**
 * @ClassName: PwdAuthenticationProvider
 * @Description: PwdAuthenticationProvider
 * @author: hmr
 * @date: 2020/5/16 13:49
 */
public class PwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    RedisTemplate redisTemplate;

//    @Autowired
//    UsernameUserDetailsServiceImpl usernameDetailsService;


    /**
     * @Author hmr
     * @Description 校验验证码是否正确
     * @Date: 2020/5/17 10:59
     * @param authentication
     * @reture: org.springframework.security.core.Authentication
     **/
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PwdAuthenticationToken pwdAuthenticationToken = (PwdAuthenticationToken) authentication;

        //用户名(登录名)
        String principal = (String) pwdAuthenticationToken.getPrincipal();
        if(principal == null || StringUtils.isBlank(principal)) throw new AuthenticationServiceException("用户名不能为空");
      // String code = pwdAuthenticationToken.getCode();
       //  if(code == null || StringUtils.isBlank(code)) throw new AuthenticationServiceException("验证码不能为空");
        String redisCode = (String) redisTemplate.opsForValue().get(RedisUtil.getWebCodeByEmail(principal));
    //    if(redisCode == null || !redisCode.equalsIgnoreCase(code)) throw new AuthenticationServiceException("验证码错误或失效");

        //UserDetails userDetails = usernameDetailsService.loadUserByUsername(principal);
        //PwdAuthenticationToken newToken = new PwdAuthenticationToken(userDetails, pwdAuthenticationToken.getCredentials(), userDetails.getAuthorities());
        return authentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (PwdAuthenticationToken.class.isAssignableFrom(aClass));
    }

}
