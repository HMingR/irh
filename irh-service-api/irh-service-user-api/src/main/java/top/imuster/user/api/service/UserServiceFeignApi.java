package top.imuster.user.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.hystrix.UserServiceFeignHystrix;

/**
 * @ClassName: UserServiceFeignApi
 * @Description: UserServiceFeignApi
 * @author: hmr
 * @date: 2020/1/27 16:33
 */
@FeignClient(name = "user-service", path = "/user/feign", fallback = UserServiceFeignHystrix.class)
public interface UserServiceFeignApi {

    @GetMapping("/login/{email}")
    UserInfo loadUserInfoByEmail(@PathVariable("email") String email);
}
