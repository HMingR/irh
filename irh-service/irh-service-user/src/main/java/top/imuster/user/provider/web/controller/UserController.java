package top.imuster.user.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.user.api.dto.CheckValidDto;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.provider.service.ReportFeedbackInfoService;
import top.imuster.user.provider.service.UserInfoService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName: CustomerController
 * @Description: customer的控制器
 * @author: hmr
 * @date: 2019/12/18 19:11
 */
@Api(tags = "用户controller,这个控制器主要是对自己信息的一些操作")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    ReportFeedbackInfoService reportFeedbackInfoService;

    @Resource
    UserInfoService userInfoService;

    /**
     * @Description 用户在注册的时候需要校验各种参数
     * @Author hmr
     * @Date 12:53 2020/1/14
     * @Param checkValidDto
     * @return top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "修改信息前需要校验各种参数(用户名、邮箱、手机号等)必须唯一",httpMethod = "POST")
    @PostMapping("/check")
    public Message<String> checkValid(@RequestBody CheckValidDto checkValidDto) throws Exception {
        boolean flag = userInfoService.checkValid(checkValidDto);
        if(flag){
            return Message.createBySuccess();
        }
        return Message.createByError(checkValidDto.getType().getTypeName() + "已经存在");
    }

    @ApiOperation("获得个人信息")
    @GetMapping("/detail")
    public Message<UserInfo> getUserInfoById(){
        List<UserInfo> userInfos = userInfoService.selectEntryList(getCurrentUserIdFromCookie());
        if(CollectionUtils.isEmpty(userInfos)) return Message.createByError("未找到相关人员信息");
        UserInfo userInfo = userInfos.get(0);
        userInfo.setPassword("");
        return Message.createBySuccess(userInfo);
    }

    /**
     * @Description: 修改会员的个人信息
     * @Author: hmr
     * @Date: 2019/12/26 19:37
     * @param userInfo
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping("/edit")
    @ApiOperation(value = "修改会员的个人信息(先校验一些信息是否存在),以表单的形式上传,不是用json,其中表单中各个标签的按钮name必须和实体类保持一致", httpMethod = "POST")
    public Message<String> editInfo(@ApiParam("ConsumerInfo实体类") @Validated(ValidateGroup.editGroup.class) @RequestBody UserInfo userInfo, BindingResult bindingResult) throws Exception{
        validData(bindingResult);
        return userInfoService.editUserInfo(userInfo);
    }

    @ApiOperation(value = "根据用户id获得用户基本信息", httpMethod = "GET")
    @GetMapping("/{id}")
    public Message<UserDto> getUserNameById(@PathVariable("id") String id){
        if(id == null || StringUtils.isEmpty(id) || "null".equals(id) || "undefined".equalsIgnoreCase(id)) return Message.createByError("参数错误");
        return userInfoService.getUserDtoByUserId(Long.parseLong(id));
    }

    @ApiOperation("查看用户账号的状态")
    @GetMapping("/state")
    public Message<Long> getUserState(){
        UserDto userInfo = getCurrentUserFromCookie();
        return Message.createBySuccess(userInfo.getUserId());
    }

    @PostMapping("/checkPic")
    public Message<String> checkPic(@RequestParam("picUri") String pciUri) throws IOException {
        return reportFeedbackInfoService.checkPic(pciUri);
    }

    @ApiOperation("更新用户头像")
    @PostMapping("/portrait")
    public Message<String> updateUserPortrait(@RequestBody String picUrl){
        Long userId = getCurrentUserIdFromCookie();
        return userInfoService.editUserPortrait(userId, picUrl);
    }


    /**
     * @Description: 会员注册
     * @Author: hmr
     * @Date: 2019/12/26 19:29
     * @param userInfo
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "会员注册,code为发送的验证码", httpMethod = "POST")
    @PostMapping("/register/{code}")
    public Message<String> register(@ApiParam("ConsumerInfo实体类") @RequestBody @Validated({ValidateGroup.register.class}) UserInfo userInfo, BindingResult bindingResult, @ApiParam("发送的验证码") @PathVariable String code) throws Exception {
        validData(bindingResult);
        return userInfoService.register(userInfo, code);
    }

    /**
     * @Author hmr
     * @Description 重置密码
     * @Date: 2020/5/19 16:20
     * @param userInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @PostMapping("/resetPwd")
    public Message<String> forgetPwd(@RequestBody UserInfo userInfo){
        return userInfoService.resetPwdByEmail(userInfo);
    }

}
