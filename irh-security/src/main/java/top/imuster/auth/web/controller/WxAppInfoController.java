package top.imuster.auth.web.controller;


import org.springframework.web.bind.annotation.*;
import top.imuster.auth.service.WxAppLoginService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.dto.UserDto;
import top.imuster.user.api.dto.BindWxDto;

import javax.annotation.Resource;

/**
 * @ClassName: WxAppInfoController
 * @Description: WxAppInfoController
 * @author: hmr
 * @date: 2020/5/19 18:17
 */
@RestController
@RequestMapping("/wx")
public class WxAppInfoController extends BaseController {

    @Resource
    WxAppLoginService wxAppLoginService;

    @GetMapping
    public Message<String> sendEmail(){
        UserDto userDto = getCurrentUserFromCookie();
        return wxAppLoginService.sendBindingEmail(userDto);
    }

    @PostMapping("/binding")
    public Message<String> bindingWx(@RequestBody BindWxDto bindDto){
        Long userId = getCurrentUserIdFromCookie();
        bindDto.setUserId(userId);
        return wxAppLoginService.binding(bindDto);
    }

    @GetMapping("/check")
    public Message<Integer> checkIsBind(){
        Long userId = getCurrentUserIdFromCookie();
        return wxAppLoginService.checkIsBind(userId);
    }

    @DeleteMapping("/unbind")
    public Message<String> unBind(){
        Long userId = getCurrentUserIdFromCookie();
        return wxAppLoginService.unbind(userId);
    }
}