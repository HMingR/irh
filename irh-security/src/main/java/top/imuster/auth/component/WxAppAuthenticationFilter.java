package top.imuster.auth.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: WxAppAuthenticationFilter
 * @Description: 微信小程序登录拦截器
 * @author: hmr
 * @date: 2020/5/10 10:23
 */
public class WxAppAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String POST = "post";

    private boolean postOnly = true;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    protected WxAppAuthenticationFilter() {
        super(new AntPathRequestMatcher("/wxAppLogin", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if(postOnly && !POST.equalsIgnoreCase(httpServletRequest.getMethod())){
            throw new AuthenticationServiceException("不允许{}这种的请求方式: " + httpServletRequest.getMethod());
        }


        return null;
    }


}
