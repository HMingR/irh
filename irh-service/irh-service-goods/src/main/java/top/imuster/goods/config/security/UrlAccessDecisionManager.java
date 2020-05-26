package top.imuster.goods.config.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import top.imuster.goods.exception.GoodsException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @ClassName: UrlAccessDecisionManager
 * @Description: 接收UrlFilterInvocationSecurityMetadataSource传过来的角色列表，并判断当前用户是否有当前权限
 * @author: hmr
 * @date: 2019/12/24 16:34
 */
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
        Collection<? extends GrantedAuthority> userRole = authentication.getAuthorities();
        ArrayList<String> roleString = new ArrayList<>();
        ArrayList<String> needRoles = new ArrayList<>();

        for (GrantedAuthority grantedAuthority : userRole) {
            roleString.add(grantedAuthority.getAuthority());
        }
        for (ConfigAttribute configAttribute : collection) {
            needRoles.add(configAttribute.getAttribute());
        }

        //查看交集,如果有交集，则证明有权限访问
        needRoles.retainAll(roleString);

        if(needRoles.size() == 0){
            throw new GoodsException("权限不足!");
        }
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
