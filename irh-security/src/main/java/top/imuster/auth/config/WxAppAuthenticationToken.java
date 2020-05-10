package top.imuster.auth.config;

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
}
