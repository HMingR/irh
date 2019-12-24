package top.imuster.user.provider.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * @ClassName: UrlAccessDecisionManager
 * @Description: 接收UrlFilterInvocationSecurityMetadataSource传过来的角色列表，并判断当前用户是否有当前权限
 * @author: hmr
 * @date: 2019/12/24 16:34
 */
@Slf4j
public class UrlAccessDecisionManager implements AccessDecisionManager {
    /**
     * @Description:
     * @Author: hmr
     * @Date: 2019/12/24 16:40
     * @param authentication 保存了当前登录用户的角色信息
     * @param o
     * @param collection UrlFilterInvocationSecurityMetadataSource中的getAttributes方法传来的，表示当前请求需要的角色
     * @reture: void
     **/
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        log.info("进入UrlAccessDecisionManager方法");
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute ca = iterator.next();
            //当前请求需要的角色
            String needRole = ca.getAttribute();
            System.out.println("访问需要的权限是" + needRole);
            log.info("访问需要的权限是--->",needRole);
            if ("ROLE_LOGIN".equals(needRole)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("未登录");
                } else
                    return;
            }

            //当前用户所具有的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole)) {
                    log.info("权限验证通过");
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
