package top.imuster.auth.component;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @ClassName: LoginSuccessHandle
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/3 16:38
 */
public class LoginSuccessHandle implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        //获取到登陆者的权限，然后做跳转
        if (roles.contains("ROLE_ADMIN")){
            response.sendRedirect("/****");
            return;
        }else if (roles.contains("ROLE_USER")){
            response.sendRedirect("/*****");
            return;
        }else {
            response.sendRedirect("/403");
        }
    }
}
