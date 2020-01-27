package top.imuster.user.provider.web.rpc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.UserServiceFeignApi;
import top.imuster.user.provider.service.UserInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: UserServiceFeignClient
 * @Description: UserServiceFeignClient
 * @author: hmr
 * @date: 2020/1/27 16:51
 */
@RestController
@RequestMapping
public class UserServiceFeignClient implements UserServiceFeignApi {

    @Resource
    UserInfoService userInfoService;

    @Override
    @GetMapping("/login/{email}")
    public UserInfo loadUserInfoByEmail(@PathVariable("email") String email) {
        return userInfoService.loadUserDetailsByEmail(email);
    }
}
