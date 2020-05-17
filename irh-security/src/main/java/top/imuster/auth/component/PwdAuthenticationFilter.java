package top.imuster.auth.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import top.imuster.auth.config.IrhAuthenticationToken;
import top.imuster.auth.config.PwdAuthenticationToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: PwdAuthenticationFilter
 * @Description: PwdAuthenticationFilter  用户名密码登录拦截器
 * @author: hmr
 * @date: 2020/5/16 13:44
 */
public class PwdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(PwdAuthenticationFilter.class);

    private static final String POST = "post";

    private boolean postOnly = true;

    //登录名
    private static final String LOGIN_PWD_NAME = "email";

    //web端的验证码
    private static final String LOGIN_PWD_CODE = "code";

    //密码
    private static final String LOGIN_PWD_PASSWORD = "password";


    public PwdAuthenticationFilter(){
        super(new AntPathRequestMatcher("/pwdLogin", "POST"));
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if(postOnly && !POST.equalsIgnoreCase(httpServletRequest.getMethod())){
            throw new AuthenticationServiceException("不允许{}这种的请求方式: " + httpServletRequest.getMethod());
        }

        //web端的验证码
        String code = obtainParameter(httpServletRequest, LOGIN_PWD_CODE);

        //用户名
        String loginName = obtainParameter(httpServletRequest, LOGIN_PWD_NAME);

        //密码
        String credentials = obtainParameter(httpServletRequest, LOGIN_PWD_PASSWORD);

        PwdAuthenticationToken pwdAuthenticationToken = new PwdAuthenticationToken(loginName, credentials);
        setDetails(httpServletRequest, pwdAuthenticationToken);
        return pwdAuthenticationToken;

    }

    private void setDetails(HttpServletRequest request,
                            IrhAuthenticationToken authRequest) {
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
