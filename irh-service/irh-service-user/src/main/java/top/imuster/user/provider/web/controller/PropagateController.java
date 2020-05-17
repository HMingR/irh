package top.imuster.user.provider.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.user.api.pojo.PropagateInfo;
import top.imuster.user.provider.service.PropagateInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: PropagateController
 * @Description: 广告和通知控制器
 * @author: hmr
 * @date: 2020/5/16 10:11
 */
@RestController
@RequestMapping("/propagate")
public class PropagateController extends BaseController {

    @Resource
    PropagateInfoService propagateInfoService;

    @NeedLogin
    @PostMapping
    public Message<String> add(@RequestBody PropagateInfo propagateInfo){
        Long userId = getCurrentUserIdFromCookie();
        propagateInfo.setPublisherId(userId);
        return propagateInfoService.release(propagateInfo);
    }


}
