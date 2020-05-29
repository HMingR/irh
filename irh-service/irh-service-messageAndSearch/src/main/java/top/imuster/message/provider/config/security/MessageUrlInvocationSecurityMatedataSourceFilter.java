package top.imuster.message.provider.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import top.imuster.common.core.security.UrlFilterInvocationSecurityMetadataSource;
import top.imuster.security.api.pojo.RoleInfo;
import top.imuster.security.api.service.RoleServiceFeignApi;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName: MessageUrlInvocationSecurityMatedataSourceFilter
 * @Description: MessageUrlInvocationSecurityMatedataSourceFilter
 * @author: hmr
 * @date: 2020/5/29 16:18
 */
public class MessageUrlInvocationSecurityMatedataSourceFilter extends UrlFilterInvocationSecurityMetadataSource {

    @Autowired
    RoleServiceFeignApi roleServiceFeignApi;

    @Override
    protected void initRoleAuthMap() {
        HashMap<String, String> authRoleMap = new HashMap<>();
        HashMap<String, String> httpMethodMap = new HashMap<>();

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
