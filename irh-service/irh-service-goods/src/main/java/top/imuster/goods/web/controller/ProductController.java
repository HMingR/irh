package top.imuster.goods.web.controller;


import cn.hutool.core.date.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.BrowseRecordAnnotation;
import top.imuster.common.core.annotation.BrowserAnnotation;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.dto.BrowseRecordDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.exception.GoodsException;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ProductController
 * @Description: 处理商品的controller
 * @author: hmr
 * @date: 2019/12/1 14:53
 */
@Api("二手商品controller")
@RestController
@RequestMapping("/goods/es")
public class ProductController extends BaseController {

    @Resource
    ProductInfoService productInfoService;

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/2/10 16:07
     * @param productInfo
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("会员发布二手商品,采用表单的形式，不采用json形式，且上传的图片的<input>或其他标签name必须是file")
    @NeedLogin
    @PutMapping
    public Message<String> insertProduct(@RequestBody @Validated(ValidateGroup.releaseGroup.class) ProductInfo productInfo, BindingResult bindingResult) throws Exception {
        validData(bindingResult);
        Long userId = getCurrentUserIdFromCookie();
        productInfo.setConsumerId(userId);
        return productInfoService.releaseProduct(productInfo);
    }

    /**
     * @Author hmr
     * @Description 根据id获得商品的简略信息
     * @Date: 2020/4/12 20:04
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @GetMapping("/brief/{id}")
    public Message<ProductInfo> getDetailById(@PathVariable("id") Long id){
        ProductInfo info = productInfoService.getProductBriefInfoById(id);
        return Message.createBySuccess(info);
    }


    /**
     * @Author hmr
     * @Description 用户查看自己发布的商品
     * @Date: 2020/4/30 9:14
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    @ApiOperation(value = "用户查看自己发布的商品", httpMethod = "GET")
    @NeedLogin
    @GetMapping("/list/{pageSize}/{currentPage}")
    public Message<Page<ProductInfo>> productList(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage) throws GoodsException{
        Long userId = getCurrentUserIdFromCookie();
        return productInfoService.list(userId, pageSize, currentPage);
    }


    /**
     * @Author hmr
     * @Description 根据userId分页查看
     * @Date: 2020/4/19 16:44
     * @param userId
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    @GetMapping("/user/{pageSize}/{currentPage}/{userId}")
    public Message<Page<ProductInfo>> productListByUserId(@PathVariable("userId") Long userId, @PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage) throws GoodsException{
        return productInfoService.list(userId, pageSize, currentPage);
    }


    /**
     * @Author hmr
     * @Description 根据id获得商品信息
     * @Date: 2020/3/14 19:44
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "根据id获得商品的所有信息", httpMethod = "GET")
    @BrowserAnnotation(browserType = BrowserType.ES_DEMAND_PRODUCT)
    @GetMapping("/{id}")
    public Message<ProductInfo> getProductById(@PathVariable("id")Long id){
        List<ProductInfo> productInfos = productInfoService.selectEntryList(id);
        if(productInfos != null && !productInfos.isEmpty()){
            return Message.createBySuccess(productInfos.get(0));
        }
        return Message.createBySuccess("为找到相关的商品,请刷新后重试");
    }


    /**
     * @Author hmr
     * @Description 修改商品信息
     * @Date: 2020/3/14 19:45
     * @param productInfo
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "修改商品信息", httpMethod = "POST")
    @PutMapping("/edit")
    public Message editProduct(@RequestBody @Validated(ValidateGroup.editGroup.class) ProductInfo productInfo, BindingResult bindingResult) throws GoodsException {
        validData(bindingResult);
        int i = productInfoService.updateByKey(productInfo);
        if(i != 0){
            return Message.createBySuccess();
        }
        return Message.createByError();
    }


    /**
     * @Description: 用户下架商品
     * @Author: hmr
     * @Date: 2019/12/27 15:11
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("用户下架商品")
    @DeleteMapping("/{id}")
    public Message<String> delProduct(@PathVariable("id") Long id){
        return productInfoService.deleteById(id, getCurrentUserIdFromCookie());
    }

    /**
     * @Author hmr
     * @Description 记录浏览和浏览次数
     * @Date: 2020/4/22 9:04
     * @param targetId
     * @reture: void
     **/
    @GetMapping("/browse/{targetId}/{type}")
    @BrowseRecordAnnotation(browserType = BrowserType.ES_SELL_PRODUCT, value = "#p2")
    @BrowserAnnotation(browserType = BrowserType.ES_SELL_PRODUCT, value = "#p0")
    public Message<String> browser(@PathVariable("targetId") Long targetId, @PathVariable("type") String detail, BrowseRecordDto recordDto){
        Long userId = getCurrentUserIdFromCookie(false);
        recordDto.setUserId(userId);
        recordDto.setTargetId(targetId);
        recordDto.setBrowserType(BrowserType.ES_SELL_PRODUCT);
        recordDto.setCreateTime(DateUtil.now());
        if("11".equals(detail)) recordDto.setScoreDetail("4.0,3.0");
        else recordDto.setScoreDetail("0.0,3.0");
        return Message.createBySuccess();
    }

}
