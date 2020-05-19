package top.imuster.auth.config;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @ClassName: WxAppAuthenticationToken
 * @Description: WxAppAuthenticationToken
 * @author: hmr
 * @date: 2020/5/10 10:35
 */
public class WxAppAuthenticationToken extends IrhAuthenticationToken  {

    public WxAppAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public WxAppAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

}
