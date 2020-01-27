package top.imuster.auth.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.auth.service.UserLoginService;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.UserServiceFeignApi;

/**
 * @ClassName: UserLoginController
 * @Description: 所有用户登录的controller
 * @author: hmr
 * @date: 2020/1/27 17:23
 */
@RestController
@RequestMapping("/")
public class UserLoginController extends BaseController {

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @Autowired
    UserLoginService userLoginService;

    @ApiOperation(value = "会员登录，成功返回token", httpMethod = "POST")
    @PostMapping("login")
    public Message<UserInfo> login(@Validated(ValidateGroup.loginGroup.class) @RequestBody UserInfo userInfo, BindingResult bindingResult){
        validData(bindingResult);
        UserInfo result = userLoginService.userLogin(userInfo.getEmail(), userInfo.getPassword());
        return Message.createBySuccess(result);
    }
}
