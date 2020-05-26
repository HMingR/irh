package top.imuster.user.provider.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import top.imuster.security.api.pojo.RoleInfo;
import top.imuster.security.api.service.RoleServiceFeignApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: UrlFilterInvocationSecurityMetadataSource
 * @Description: 控制角色对资源的访问，该类的作用就是查看当前访问的url是否在数据库中被注册，如果被注册，则需要权限访问
 * @author: hmr
 * @date: 2019/12/24 15:03
 */
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Value("${enable.needLogin}")
    boolean enable;


    @Autowired
    RoleServiceFeignApi roleServiceFeignApi;

    @Autowired
    AntPathMatcher antPathMatcher;

    @Autowired
    IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if(!enable){
            return null;
        }

        String requestUrl = ((FilterInvocation) o).getRequestUrl();

        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();

        if(requestUrl.startsWith("/user/feign")){
            return null;
        }

        if(ignoreUrls.contains(requestUrl)){
            return null;
        }
        String method = ((FilterInvocation) o).getRequest().getMethod();

        ArrayList<String> roles = new ArrayList<>();
        List<RoleInfo> roleAndAuthList = roleServiceFeignApi.getRoleAndAuthList();

        //将数据库中所有的角色都拿到，然后遍历每每一个角色的权限，如果能匹配上，则返回该角色的名称
        roleAndAuthList.stream().forEach(roleInfo -> {
            roleInfo.getAuthInfoList().stream().forEach(authInfo -> {
                if(authInfo != null){
                    if(antPathMatcher.match(authInfo.getAuthDesc(), requestUrl)){
                        roles.add(roleInfo.getRoleName());
                    }
                }
            });
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
