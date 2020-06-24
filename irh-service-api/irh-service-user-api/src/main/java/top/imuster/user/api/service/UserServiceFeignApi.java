package top.imuster.user.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.imuster.user.api.dto.UserAuthenResultDto;
import top.imuster.user.api.pojo.ExamineRecordInfo;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.hystrix.UserServiceFeignHystrix;

import java.util.Map;

/**
 * @ClassName: UserServiceFeignApi
 * @Description: UserServiceFeignApi
 * @author: hmr
 * @date: 2020/1/27 16:33
 */
@FeignClient(name = "user-service", path = "/user/feign", fallbackFactory = UserServiceFeignHystrix.class)
public interface UserServiceFeignApi {

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

    @PostMapping("/examine")
    boolean saveExamineRecord2DB(@RequestBody ExamineRecordInfo examineRecordInfo);


    @GetMapping("/login/{email}")
    UserInfo loadUserInfoByEmail(@PathVariable("email") String email);

    @GetMapping("/wxLogin/{id}")
    UserInfo getInfoById(@PathVariable("id") Long userId);

    /**
     * @Author hmr
     * @Description 用户实名认证成功
     * @Date: 2020/5/30 17:39
     * @param userInfo
     * @reture: java.lang.Integer
     **/
    @PostMapping("/auth/success")
    Integer userAuthenSuccess(@RequestBody UserAuthenResultDto userAuthenResultDto);
}
