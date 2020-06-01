package top.imuster.security.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import top.imuster.user.api.service.hystrix.UserServiceFeignHystrix;

/**
 * @ClassName: RoleServiceFeignApi
 * @Description: RoleServiceFeignApi
 * @author: hmr
 * @date: 2020/5/26 10:23
 */
@FeignClient(name = "security-service", path = "/security/feign", fallbackFactory = UserServiceFeignHystrix.class)
public interface RoleServiceFeignApi {

    @GetMapping("/init")
    Boolean init();
}
