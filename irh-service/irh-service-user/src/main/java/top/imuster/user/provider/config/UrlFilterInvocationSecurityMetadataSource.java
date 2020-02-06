package top.imuster.user.provider.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import top.imuster.user.api.pojo.AuthInfo;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.provider.service.RoleInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: UrlFilterInvocationSecurityMetadataSource
 * @Description: 控制角色对资源的访问，该类的作用就是查看当前访问的url是否在数据库中被注册，如果被注册，则需要权限访问
 * @author: hmr
 * @date: 2019/12/24 15:03
 */
@Slf4j
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

    @Value("${enable.needLogin}")
    boolean enable;

    @Resource
    RoleInfoService roleInfoService;

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
        log.info("访问的地址是{},httpMethod为{}", requestUrl, method);

        ArrayList<String> roles = new ArrayList<>();
        List<RoleInfo> roleAndAuthList = roleInfoService.getRoleAndAuthList();

        //将数据库中所有的角色都拿到，然后遍历每每一个角色的权限，如果能匹配上，则返回该角色的名称
        roleAndAuthList.stream().forEach(roleInfo -> {
            roleInfo.getAuthInfoList().stream().forEach(authInfo -> {
                if(antPathMatcher.match(authInfo.getAuthDesc(), requestUrl)){
                    roles.add(roleInfo.getRoleName());
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
