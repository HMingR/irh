package top.imuster.life.provider.web.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.life.api.pojo.ErrandInfo;
import top.imuster.life.provider.service.ErrandInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: ErrandController
 * @Description: 跑腿服务controller
 * @author: hmr
 * @date: 2020/2/11 19:32
 */
@Api
@RestController
@RequestMapping("/errand")
public class ErrandController extends BaseController {

    @Resource
    private ErrandInfoService errandInfoService;

    @ApiOperation("发布跑腿")
    @NeedLogin
    @PostMapping
    public Message<String> release(@RequestBody ErrandInfo errandInfo){
        Long userId = getCurrentUserIdFromCookie();
        errandInfo.setPublisherId(userId);
        errandInfoService.insertEntry(errandInfo);
        return Message.createBySuccess();
    }

    @ApiOperation("删除自己的跑腿服务")
    @NeedLogin
    @DeleteMapping("/{id}")
    public Message<String> delete(@PathVariable("id") Long id){
        Long userId = getCurrentUserIdFromCookie();
        ErrandInfo condition = new ErrandInfo();
        condition.setId(id);
        condition.setPublisherId(userId);
        errandInfoService.updateByKey(condition);
        return Message.createBySuccess();
    }

    @ApiOperation("查看自己发布的跑腿服务")
    @PostMapping("/list")
    @NeedLogin
    public Message<Page<ErrandInfo>> list(@RequestBody Page<ErrandInfo> page){
        Long userId = getCurrentUserIdFromCookie();
        return errandInfoService.getListByCondition(page, userId);
    }
}
