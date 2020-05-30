package top.imuster.goods.web.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.goods.service.ErrandInfoService;
import top.imuster.life.api.pojo.ErrandInfo;

import javax.annotation.Resource;
import java.util.List;

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
    @PostMapping
    public Message<String> release(@Validated(ValidateGroup.addGroup.class) @RequestBody ErrandInfo errandInfo, BindingResult bindingResult){
        validData(bindingResult);
        return errandInfoService.release(errandInfo, getCurrentUserIdFromCookie());
    }

    @ApiOperation("删除自己的跑腿服务")
    @DeleteMapping("/{id}/{version}")
    public Message<String> delete(@PathVariable("id") Long id, @PathVariable("version") Integer version){
        return errandInfoService.deleteErrandById(id, getCurrentUserIdFromCookie(), version);
    }

    /**
     * @Author hmr
     * @Description 查看和自己有关的跑腿
     * @Date: 2020/5/13 18:08
     * @param state 2-还没有被接单的  3-已经被接单   4-已完成
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.life.api.pojo.ErrandInfo>>
     **/
    @ApiOperation("查看自己发布的跑腿服务")
    @GetMapping("/list/{pageSize}/{currentPage}/{state}")
    @NeedLogin
    public Message<Page<ErrandInfo>> list(@PathVariable("pageSize") Integer pageSize,
                                          @PathVariable("currentPage") Integer currentPage,
                                          @PathVariable("state") Integer state){
        if((state != 2 && state != 3 && state != 4) || pageSize < 1 || currentPage < 1) return Message.createByError("参数错误");
        Long userId = getCurrentUserIdFromCookie();
        return errandInfoService.getListByCondition(pageSize, currentPage, userId, state);
    }

    @GetMapping("/detail/{id}")
    public Message<ErrandInfo> getDetail(@PathVariable("id") Long id){
        List<ErrandInfo> errandInfos = errandInfoService.selectEntryList(id);
        if(errandInfos == null || errandInfos.isEmpty()) return Message.createByError("未找到相关订单,请稍后重试");
        ErrandInfo errandInfo = errandInfos.get(0);
        errandInfo.setCypher("");
        errandInfo.setPhoneNum("");
        errandInfo.setAddress("");
        return Message.createBySuccess(errandInfo);
    }

    /**
     * @Author hmr
     * @Description 根据type查看所有可用的跑腿列表
     * @Date: 2020/5/10 15:34
     * @param pageSize
     * @param type 1-时间  2-金额
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.life.api.pojo.ErrandInfo>>
     **/
    @GetMapping("/{type}/{pageSize}/{currentPage}")
    public Message<Page<ErrandInfo>> listByType(@PathVariable("pageSize") Integer pageSize,
                                                @PathVariable("type") Integer type,
                                                @PathVariable("currentPage") Integer currentPage){
        if(type != 1 && type != 2) return Message.createByError("参数错误,请刷新后重试");
        if(pageSize < 1 || currentPage < 1) return Message.createByError("参数错误");
        return errandInfoService.listByType(pageSize, currentPage, type);
    }
}
