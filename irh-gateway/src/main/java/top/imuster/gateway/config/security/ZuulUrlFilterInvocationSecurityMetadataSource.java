package top.imuster.gateway.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.AntPathMatcher;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.security.api.service.RoleServiceFeignApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ZuulUrlFilterInvocationSecurityMetadataSource
 * @Description: 控制角色对资源的访问，该类的作用就是查看当前访问的url是否在数据库中被注册，如果被注册，则需要权限访问
 * @author: hmr
 * @date: 2019/12/24 15:03
 */
public class ZuulUrlFilterInvocationSecurityMetadataSource extends AbstractUrlFilterInvocationSecurityMetadataSource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AntPathMatcher antPathMatcher;

    @Autowired
    IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    RoleServiceFeignApi roleServiceFeignApi;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected void initRoleAuthMap() {
        Map<String, String> authRoleMap = new HashMap<>();
        Map<String, String> httpMethodMap = new HashMap<>();
        roleServiceFeignApi.init();
        String auth = (String)redisTemplate.opsForValue().get(GlobalConstant.ROLE_AUTH_MAP_KEY);
        String methodType = (String)redisTemplate.opsForValue().get(GlobalConstant.AUTH_HTTP_METHOD_MAP_KEY);
        try {
            authRoleMap = objectMapper.readValue(auth, HashMap.class);
            httpMethodMap = objectMapper.readValue(methodType, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAuthRoleMap(authRoleMap);
        setHttpMethodMap(httpMethodMap);
    }

    public void resetMap(){
        setAuthRoleMap(null);
        setHttpMethodMap(null);
    }

}
