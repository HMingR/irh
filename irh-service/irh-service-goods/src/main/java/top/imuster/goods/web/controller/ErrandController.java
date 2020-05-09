package top.imuster.goods.web.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
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
    public Message<String> release(@RequestBody ErrandInfo errandInfo){
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
}
