package top.imuster.auth.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import top.imuster.auth.config.WxAppAuthenticationToken;

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

    private static final String WX_APP_CODE = "code";

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    public WxAppAuthenticationFilter() {
        super(new AntPathRequestMatcher("/wxAppLogin", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if(postOnly && !POST.equalsIgnoreCase(httpServletRequest.getMethod())){
            throw new AuthenticationServiceException("不允许{}这种的请求方式: " + httpServletRequest.getMethod());
        }

        //前端生成的临时code
        String code = obtainParameter(httpServletRequest, WX_APP_CODE);
        if(StringUtils.isBlank(code)) throw new AuthenticationServiceException("参数错误");

        WxAppAuthenticationToken wxAppAuthenticationToken = new WxAppAuthenticationToken(null, code);
        setDetails(httpServletRequest, wxAppAuthenticationToken);
        return authenticationManager.authenticate(wxAppAuthenticationToken);
    }

    private void setDetails(HttpServletRequest request,
                            WxAppAuthenticationToken authRequest) {
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
