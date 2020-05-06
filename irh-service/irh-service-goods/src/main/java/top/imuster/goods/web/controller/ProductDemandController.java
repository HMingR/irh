package top.imuster.goods.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.BrowserAnnotation;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.service.ProductDemandInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: DemandProductController
 * @Description: 会员自己发布自己的需求
 * @author: hmr
 * @date: 2019/12/31 20:35
 */
@Api("会员发布的需求")
@RestController
@RequestMapping("/goods/demand")
//@PropertySource("classpath:application.yml")
public class ProductDemandController extends BaseController {
    @Resource
    ProductDemandInfoService productDemandInfoService;

    @ApiOperation(value = "发布需求", httpMethod = "PUT")
    @NeedLogin
    @PutMapping
    public Message<String> add(@RequestBody @Validated(ValidateGroup.releaseGroup.class) ProductDemandInfo productDemandInfo, BindingResult bindingResult) {
        validData(bindingResult);
        Long userId = getCurrentUserIdFromCookie();
        return productDemandInfoService.releaseDemand(productDemandInfo, userId);
    }

    @ApiOperation("分页查看自己发布的需求")
    @NeedLogin
    @GetMapping("/list/{pageSize}/{currentPage}")
    public Message<Page<ProductDemandInfo>> getList(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        Long userId = getCurrentUserIdFromCookie();
        return productDemandInfoService.list(userId, pageSize, currentPage);
    }

    @ApiOperation(value = "根据id查询", httpMethod = "GET")
    @GetMapping("/{id}")
    public Message<ProductDemandInfo> getById(@PathVariable("id") Long id){
        List<ProductDemandInfo> productDemandInfos = productDemandInfoService.selectEntryList(id);
        if(productDemandInfos == null || productDemandInfos.isEmpty()) return Message.createBySuccess();
        return Message.createBySuccess(productDemandInfos.get(0));
    }

    @ApiOperation(value = "根据主键id修改信息", httpMethod = "POST")
    @NeedLogin
    @PostMapping
    public Message<String> edit(@RequestBody @Validated(ValidateGroup.editGroup.class) ProductDemandInfo productDemandInfo, BindingResult bindingResult){
        validData(bindingResult);
        productDemandInfo.setConsumerId(getCurrentUserIdFromCookie());
        productDemandInfoService.updateByKey(productDemandInfo);
        return Message.createBySuccess("修改成功");
    }

    @ApiOperation(value = "删除用户自己发布的需求", httpMethod = "DELETE")
    @NeedLogin
    @DeleteMapping("/{id}")
    public Message<String> delete(@PathVariable("id") Long id){
        Long userId = getCurrentUserIdFromCookie();
        return productDemandInfoService.deleteById(id, userId);
    }

    /**
     * @Author hmr
     * @Description 根据用户id分页查看该用户发布的需求
     * @Date: 2020/4/19 18:17
     * @param pageSize
     * @param currentPage
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductDemandInfo>>
     **/
    @GetMapping("/user/{pageSize}/{currentPage}/{userId}")
    public Message<Page<ProductDemandInfo>> getListByUserId(@PathVariable("pageSize")Integer pageSize, @PathVariable("currentPage") Integer currentPage, @PathVariable("userId") Long userId){
        return productDemandInfoService.list(userId, pageSize, currentPage);
    }

    /**
     * @Author hmr
     * @Description 记录浏览
     * @Date: 2020/4/22 9:07
     * @param targetId
     * @reture: void
     **/
    @BrowserAnnotation(browserType = BrowserType.ES_DEMAND_PRODUCT, value = "#p0")
    @GetMapping("/browser/{targetId}")
    public void browser(@PathVariable("targetId") Long targetId){

    }
}
