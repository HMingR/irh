package top.imuster.user.provider.web.rpc;

import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.UserServiceFeignApi;
import top.imuster.user.provider.service.RoleInfoService;
import top.imuster.user.provider.service.UserInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: UserServiceFeignClient
 * @Description: UserServiceFeignClient
 * @author: hmr
 * @date: 2020/1/27 16:51
 */
@RestController
@RequestMapping("/user/feign")
public class UserServiceFeignClient implements UserServiceFeignApi {

    @Resource
    RoleInfoService roleInfoService;

    @Resource
    UserInfoService userInfoService;

    @Override
    @GetMapping("/login/{email}")
    public UserInfo loadUserInfoByEmail(@PathVariable("email") String email) {
        return userInfoService.loadUserDetailsByEmail(email);
    }

    @Override
    @GetMapping("/roleAndAuth")
    public List<RoleInfo> getAllRoleAndAuth() {
        return roleInfoService.getRoleAndAuthList();
    }

    @Override
    @GetMapping("/userRole/{loginName}")
    public List<String> getRoleByUserName(@PathVariable("loginName") String loginName) {
        return roleInfoService.getRoleNameByUserName(loginName);
    }

    @Override
    @PostMapping("/register")
    public Message<String> register(@RequestBody UserInfo userInfo,@PathVariable("code") String code) {
        return userInfoService.register(userInfo, code);
    }
}
