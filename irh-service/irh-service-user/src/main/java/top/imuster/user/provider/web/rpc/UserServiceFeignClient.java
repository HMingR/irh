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
import java.util.Map;

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
    public Message<String> register(@RequestBody UserInfo userInfo) {
        return userInfoService.register(userInfo);
    }

    @Override
    @GetMapping("/{userId}/{state}")
    public Message<String> updateUserState(@PathVariable("userId") Long userId, @PathVariable("state") Integer state) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setState(state);
        userInfoService.updateByKey(userInfo);
        return Message.createBySuccess();
    }

    @Override
    @GetMapping("/addAndPhone/{userId}")
    public Map<String, String> getUserAddressAndPhoneById(@PathVariable("userId") Long userId) {
        return userInfoService.getAddAndPhoneById(userId);
    }

    @Override
    @GetMapping("/email/{id}")
    public String getUserEmailById(@PathVariable("id") Long holderId) {
        return userInfoService.getEmailById(holderId);
    }

    @Override
    @GetMapping("/reset/{email}/{pwd}")
    public boolean updateUserPwdByEmail(@PathVariable("email") String email, @PathVariable("pwd") String password){
        return userInfoService.resetPwdByEmail(email, password);
    }
}
