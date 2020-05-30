package top.imuster.order.provider.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import top.imuster.common.core.security.UrlFilterInvocationSecurityMetadataSource;
import top.imuster.security.api.pojo.RoleInfo;
import top.imuster.security.api.service.RoleServiceFeignApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: OrderUrlFilterInvocationSecurityMetadataSource
 * @Description: 控制角色对资源的访问，该类的作用就是查看当前访问的url是否在数据库中被注册，如果被注册，则需要权限访问
 * @author: hmr
 * @date: 2019/12/24 15:03
 */
public class OrderUrlFilterInvocationSecurityMetadataSource extends UrlFilterInvocationSecurityMetadataSource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${enable.needLogin}")
    boolean enable;

    @Autowired
    AntPathMatcher antPathMatcher;

    @Autowired
    IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    RoleServiceFeignApi roleServiceFeignApi;

    @Override
    protected void initRoleAuthMap() {
        Map<String, String> authRoleMap = new HashMap<>();
        Map<String, String> httpMethodMap = new HashMap<>();
        List<RoleInfo> roleAndAuthList = roleServiceFeignApi.getRoleAndAuthList();
        //将数据库中所有的角色都拿到，然后遍历每每一个角色的权限，如果能匹配上，则返回该角色的名称
        roleAndAuthList.stream().forEach(roleInfo -> {
            roleInfo.getAuthInfoList().stream().forEach(authInfo -> {
                if(authInfo != null){
                    String authDesc = authInfo.getAuthDesc();
                    String roleName = roleInfo.getRoleName();
                    authRoleMap.put(authDesc, roleName);
                    httpMethodMap.put(authDesc, authInfo.getHttpMethod());
                }
            });
        });
        setAuthRoleMap(authRoleMap);
        setHttpMethodMap(httpMethodMap);
    }

}
