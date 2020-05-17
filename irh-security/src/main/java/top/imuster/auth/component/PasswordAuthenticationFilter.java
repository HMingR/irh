package top.imuster.auth.component;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import top.imuster.auth.config.PasswordCodeAuthenticationToken;
import top.imuster.auth.config.SecurityConstants;

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
public class PasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(PasswordAuthenticationFilter.class);

    private static final String POST = "post";

    private boolean postOnly = true;

    public PasswordAuthenticationFilter(){
        super(new AntPathRequestMatcher("/password", "POST"));
    }

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

        if(StringUtils.isBlank(loginName)) throw new AuthenticationServiceException("登录名不能为空");
        if(StringUtils.isBlank(credentials)) throw new AuthenticationServiceException("验证码不能为空");

        PasswordCodeAuthenticationToken authenticationToken = new PasswordCodeAuthenticationToken(loginName, credentials);;
        setDetails(httpServletRequest, authenticationToken);
        return authenticationManager.authenticate(authenticationToken);
    }

    private void setDetails(HttpServletRequest request,
                            PasswordCodeAuthenticationToken authRequest) {
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
