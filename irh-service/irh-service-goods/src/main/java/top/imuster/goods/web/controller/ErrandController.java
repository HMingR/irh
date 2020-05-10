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
    public Message<String> release(@Validated(ValidateGroup.addGroup.class) @RequestBody ErrandInfo errandInfo, BindingResult bindingResult){
        validData(bindingResult);
        return errandInfoService.release(errandInfo, getCurrentUserIdFromCookie());
    }

    @ApiOperation("删除自己的跑腿服务")
    @NeedLogin
    @DeleteMapping("/{id}")
    public Message<String> delete(@PathVariable("id") Long id){
        return errandInfoService.deleteErrandById(id, getCurrentUserIdFromCookie());
    }

    @ApiOperation("查看自己发布的跑腿服务")
    @GetMapping("/list/{pageSize}/{currentPage}")
    @NeedLogin
    public Message<Page<ErrandInfo>> list(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        Long userId = getCurrentUserIdFromCookie();
        return errandInfoService.getListByCondition(pageSize, currentPage, userId);
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
