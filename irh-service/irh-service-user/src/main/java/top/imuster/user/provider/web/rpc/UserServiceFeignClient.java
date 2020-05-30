package top.imuster.user.provider.web.rpc;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.dto.UserAuthenResultDto;
import top.imuster.user.api.pojo.ExamineRecordInfo;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.UserServiceFeignApi;
import top.imuster.user.provider.service.ExamineRecordInfoService;
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
    UserInfoService userInfoService;

    @Resource
    ExamineRecordInfoService examineRecordInfoService;

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

    @PostMapping("/examine")
    public boolean saveExamineRecord2DB(@RequestBody ExamineRecordInfo examineRecordInfo){
        int i = examineRecordInfoService.insertEntry(examineRecordInfo);
        return i != 0;
    }

    @Override
    @GetMapping("/login/{email}")
    public UserInfo loadUserInfoByEmail(@PathVariable("email") String email) {
        return userInfoService.loadUserDetailsByEmail(email);
    }

    @Override
    public UserInfo getInfoById(Long userId) {
        List<UserInfo> userInfos = userInfoService.selectEntryList(userId);
        if(CollectionUtils.isNotEmpty(userInfos)) return userInfos.get(0);
        return null;
    }

    @PostMapping("/auth/success")
    public Integer userAuthenSuccess(@RequestBody UserAuthenResultDto userAuthenResultDto){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userAuthenResultDto.getUserId());
        userInfo.setAcademyName(userAuthenResultDto.getAcademyName());
        userInfo.setState(40);
        int i = userInfoService.updateByKey(userInfo);
        return i;
    }
}
