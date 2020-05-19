package top.imuster.user.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.hystrix.UserServiceFeignHystrix;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserServiceFeignApi
 * @Description: UserServiceFeignApi
 * @author: hmr
 * @date: 2020/1/27 16:33
 */
@FeignClient(name = "user-service", path = "/user/feign", fallbackFactory = UserServiceFeignHystrix.class)
public interface UserServiceFeignApi {

    @GetMapping("/login/{email}")
    UserInfo loadUserInfoByEmail(@PathVariable("email") String email);

    @GetMapping("/roleAndAuth")
    List<RoleInfo> getAllRoleAndAuth();

    /**
     * @Author hmr
     * @Description 通过用户登录名获得用户的角色
     * @Date: 2020/1/30 17:52
     * @param name
     * @reture: java.util.List<java.lang.String>
     **/
    @GetMapping("/userRole/{loginName}")
    List<String> getRoleByUserName(@PathVariable("loginName") String loginName);

    /**
     * @Author hmr
     * @Description 更新用户的状态
     * @Date: 2020/3/29 16:21
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @GetMapping("/{userId}/{state}")
    Message<String> updateUserState(@PathVariable("userId") Long userId, @PathVariable("state") Integer state);

    /**
     * @Author hmr
     * @Description 根据id获得用户的电话和地址
     * @Date: 2020/5/9 14:53
     * @param userId
     * @reture: java.util.Map<java.lang.String,java.lang.String>  返回的key为:address , phoneNum
     **/
    @GetMapping("/addAndPhone/{userId}")
    Map<String, String> getUserAddressAndPhoneById(@PathVariable("userId") Long userId);

    /**
     * @Author hmr
     * @Description 根据id获得email
     * @Date: 2020/5/9 15:48
     * @param holderId
     * @reture: java.lang.Long
     **/
    @GetMapping("/email/{id}")
    String getUserEmailById(@PathVariable("id") Long holderId);

    @GetMapping("/wxLogin/{openId}")
    UserInfo getInfoByWxOpenId(@PathVariable("openId") String openId);
}
