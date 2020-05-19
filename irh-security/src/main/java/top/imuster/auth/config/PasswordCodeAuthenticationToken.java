package top.imuster.auth.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @ClassName: SmsCodeAuthenticationToken
 * @Description: 验证码登录验证信息封装类
 * @author: hmr
 * @date: 2020/4/30 13:59
 */
public class PasswordCodeAuthenticationToken extends UsernamePasswordAuthenticationToken {

    //web端的验证码
    protected String webCode;

    public PasswordCodeAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public PasswordCodeAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public String getWebCode() {
        return webCode;
    }

    public void setWebCode(String webCode) {
        this.webCode = webCode;
    }
}
