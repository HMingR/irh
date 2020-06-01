package top.imuster.auth.web.rpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.auth.service.RoleInfoService;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.security.api.pojo.RoleInfo;
import top.imuster.security.api.service.RoleServiceFeignApi;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RoleServiceFeignServiceImpl
 * @Description: RoleServiceFeignServiceImpl
 * @author: hmr
 * @date: 2020/5/26 10:29
 */
@RestController
@RequestMapping("/security/feign")
public class RoleServiceFeignServiceImpl implements RoleServiceFeignApi {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    RoleInfoService roleInfoService;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @GetMapping("/init")
    public Boolean init() {
        List<RoleInfo> roleAndAuthList = roleInfoService.getRoleAndAuthList();
        Map<String, String> authRoleMap = new HashMap<>();
        Map<String, String> httpMethodMap = new HashMap<>();
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
        String auth = null;
        String http = null;
        try {
            auth = objectMapper.writeValueAsString(authRoleMap);
            http = objectMapper.writeValueAsString(httpMethodMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        redisTemplate.opsForValue().set(GlobalConstant.ROLE_AUTH_MAP_KEY, auth);
        redisTemplate.opsForValue().set(GlobalConstant.AUTH_HTTP_METHOD_MAP_KEY, http);
        return true;
    }
}
