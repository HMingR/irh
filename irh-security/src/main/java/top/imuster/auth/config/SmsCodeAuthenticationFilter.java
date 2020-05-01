package top.imuster.auth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import top.imuster.auth.component.SmsUserDetailsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: SmsCodeAuthenticationFilter
 * @Description: 自定义拦截器，拦截登录请求中的登录类型
 * @author: hmr
 * @date: 2020/4/30 12:16
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(SmsCodeAuthenticationFilter.class);

    private static final String POST = "post";

    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter(){
        super(new AntPathRequestMatcher("/emailCodeLogin", "POST"));
    }

    @Autowired
    SmsUserDetailsService smsUserDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if(postOnly && !POST.equalsIgnoreCase(httpServletRequest.getMethod())){
            throw new AuthenticationServiceException("不允许{}这种的请求方式: " + httpServletRequest.getMethod());
        }
        //邮箱地址
        String loginName = obtainParameter(httpServletRequest, SecurityConstants.LOGIN_PARAM_NAME);
        //验证码
        String credentials = obtainParameter(httpServletRequest, SecurityConstants.EMAIL_VERIFY_CODE);
        loginName = loginName.trim();
        SmsCodeAuthenticationToken authenticationToken = new SmsCodeAuthenticationToken(loginName, credentials);;
        setDetails(httpServletRequest, authenticationToken);
        return authenticationManager.authenticate(authenticationToken);
    }

    private void setDetails(HttpServletRequest request,
                            SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * @Author hmr
     * @Description 从request中获得参数
     * @Date: 2020/4/30 12:22
     * @param request
     * @reture: java.lang.String
     **/
    protected String obtainParameter(HttpServletRequest request, String type){
        return request.getParameter(type);
    }
}
