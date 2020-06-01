package top.imuster.gateway.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.*;

/**
 * @ClassName: AbstractUrlFilterInvocationSecurityMetadataSource
 * @Description: 控制角色对资源的访问，该类的作用就是查看当前访问的url是否在数据库中被注册，如果被注册，则需要权限访问
 * @author: hmr
 * @date: 2019/12/24 15:03
 */
public abstract class AbstractUrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AntPathMatcher antPathMatcher;


    List<String> ignoreUrls = new ArrayList<>();

    volatile private static Map<String, String> authRoleMap;

    //由于是Rest风格的接口，相同的uri有不同的功能
    volatile private static Map<String, String> httpMethodMap;

    abstract protected void initRoleAuthMap();

    public static void setAuthRoleMap(Map<String, String> authRoleMap) {
        AbstractUrlFilterInvocationSecurityMetadataSource.authRoleMap = authRoleMap;
    }

    public static void setHttpMethodMap(Map<String, String> httpMethodMap) {
        AbstractUrlFilterInvocationSecurityMetadataSource.httpMethodMap = httpMethodMap;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        if(authRoleMap == null || authRoleMap.size() == 0) initRoleAuthMap();

        String requestUrl = ((FilterInvocation) o).getRequestUrl();

        if(requestUrl.startsWith("/**/feign")) return null;

        if(ignoreUrls != null && ignoreUrls.contains(requestUrl)) return null;

        if(authRoleMap == null || httpMethodMap == null) return null;

        String method = ((FilterInvocation) o).getRequest().getMethod();

        //将数据库中所有的角色都拿到，然后遍历每每一个角色的权限，如果能匹配上，则返回该角色的名称
        ArrayList<String> roles = new ArrayList<>();
        Set<String> urls = authRoleMap.keySet();
        urls.stream().forEach(u -> {
            String httpMethod = httpMethodMap.get(u);
            if(antPathMatcher.match(u, requestUrl)  && method.equalsIgnoreCase(httpMethod)){
                roles.add(authRoleMap.get(u));
            }
        });

        String[] res = roles.toArray(new String[roles.size()]);
        return SecurityConfig.createList(res);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
