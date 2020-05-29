package top.imuster.user.provider.web.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.BrowserAnnotation;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.user.api.pojo.PropagateInfo;
import top.imuster.user.provider.service.PropagateInfoService;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * @Author hmr
     * @Description 发布广告或者通知
     * @Date: 2020/5/25 10:15
     * @param propagateInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @PostMapping("/admin")
    public Message<String> add(@RequestBody PropagateInfo propagateInfo){
        Long userId = getCurrentUserIdFromCookie();
        propagateInfo.setPublisherId(userId);
        return propagateInfoService.release(propagateInfo);
    }

    @GetMapping("/{pageSize}/{currentPage}/{type}")
    public Message<Page<PropagateInfo>> getBriefList(@PathVariable("type") Integer type, @PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        return propagateInfoService.getBriefList(pageSize, currentPage, type);
    }

    @GetMapping("/detail/{id}")
    public Message<PropagateInfo> getDetail(@PathVariable("id") Long id){
        List<PropagateInfo> propagateInfos = propagateInfoService.selectEntryList(id);
        if(CollectionUtils.isNotEmpty(propagateInfos)) return Message.createBySuccess(propagateInfos.get(0));
        return Message.createByError("未找到相关信息");
    }

    @BrowserAnnotation(browserType = BrowserType.PROPAGATE, value = "#p0")
    @GetMapping("/browse/{targetId}")
    public void browse(@PathVariable("targetId") Long targetId){

    }


}
