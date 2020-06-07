package top.imuster.auth.component.login;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import top.imuster.auth.config.PasswordCodeAuthenticationToken;

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

    private static final String PASSWORD_PARAM_NAME = "loginName";

    private static final String PASSWORD_PARAM_WEB_CODE = "webCode";

    private static final String PASSWORD_PARAM_PASSWORD = "password";

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
        String loginName = obtainParameter(httpServletRequest, PASSWORD_PARAM_NAME);

        //验证码
        String webCode = obtainParameter(httpServletRequest, PASSWORD_PARAM_WEB_CODE);

        //密码
        String credentials = obtainParameter(httpServletRequest, PASSWORD_PARAM_PASSWORD);
        if(StringUtils.isBlank(loginName)) throw new AuthenticationServiceException("登录名不能为空");
        if(StringUtils.isBlank(credentials)) throw new AuthenticationServiceException("密码不能为空");
        if(StringUtils.isBlank(webCode)) throw new AuthenticationServiceException("验证码不能为空");
        loginName = loginName.trim();
        PasswordCodeAuthenticationToken authenticationToken = new PasswordCodeAuthenticationToken(loginName, credentials);;
        authenticationToken.setWebCode(webCode);

        setDetails(httpServletRequest, authenticationToken);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    private void setDetails(HttpServletRequest request,
                            AbstractAuthenticationToken authenticationToken) {
        authenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
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
