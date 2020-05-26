package top.imuster.security.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import top.imuster.security.api.pojo.RoleInfo;
import top.imuster.user.api.service.hystrix.UserServiceFeignHystrix;

import java.util.List;

/**
 * @ClassName: RoleServiceFeignApi
 * @Description: RoleServiceFeignApi
 * @author: hmr
 * @date: 2020/5/26 10:23
 */
@FeignClient(name = "security-service", path = "/security/feign", fallbackFactory = UserServiceFeignHystrix.class)
public interface RoleServiceFeignApi {

    /**
     * @Author hmr
     * @Description 获得所有的角色和权限
     * @Date: 2020/5/26 10:27
     * @param
     * @reture: java.util.List<top.imuster.user.api.pojo.RoleInfo>
     **/
    @GetMapping
    List<RoleInfo> getRoleAndAuthList();
}
