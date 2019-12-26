package top.imuster.user.provider.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import top.imuster.auth.config.IgnoreUrlsConfig;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.provider.service.RoleInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: UrlFilterInvocationSecurityMetadataSource
 * @Description: 控制角色对资源的访问
 * @author: hmr
 * @date: 2019/12/24 15:03
 */
@Slf4j
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{

    @Resource
    RoleInfoService roleInfoService;

    @Autowired
    AntPathMatcher antPathMatcher;

    @Autowired
    IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        log.info("访问的地址是{}", requestUrl);
        //登录
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        if(ignoreUrls.contains(requestUrl)){
            return null;
        }
        ArrayList<String> roles = new ArrayList<>();
        List<RoleInfo> roleAndAuthList = roleInfoService.getRoleAndAuthList();
        roleAndAuthList.stream().forEach(roleInfo -> {
            roleInfo.getAuthInfoList().stream().forEach(authInfo -> {
                if(antPathMatcher.match(authInfo.getAuthDesc(), requestUrl)){
                    roles.add(roleInfo.getRoleName());
                }
            });
        });

        int index = 0;
        String[] res = new String[roles.size()];
        for (String role : roles) {
            res[index++] = role;
        }
        log.info("获得的角色列表是{}", res);
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
