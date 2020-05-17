package top.imuster.auth.config;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @ClassName: PwdAuthenticationToken
 * @Description: 用户名密码登录需要用到的token
 * @author: hmr
 * @date: 2020/5/16 13:45
 */
public class PwdAuthenticationToken extends IrhAuthenticationToken {


    public PwdAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public PwdAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
