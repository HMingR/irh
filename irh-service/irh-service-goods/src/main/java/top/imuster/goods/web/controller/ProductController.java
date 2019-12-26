package top.imuster.goods.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.config.GoodsException;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: ProductController
 * @Description: 处理商品的controller
 * @author: hmr
 * @date: 2019/12/1 14:53
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Resource
    ProductInfoService productInfoService;

    @PostMapping("/list")
    public Message productList(Page<ProductInfo> page, ProductInfo productInfo) throws GoodsException{
        Page<ProductInfo> productInfoPage = productInfoService.selectPage(productInfo, page);
        return Message.createBySuccess(productInfoPage);
    }

    @PostMapping("/edit")
    public Message editProduct(@RequestBody @Validated(ValidateGroup.editGroup.class) ProductInfo productInfo, BindingResult bindingResult) throws GoodsException {
        validData(bindingResult);
        int i = productInfoService.updateByKey(productInfo);
        if(i != 0){
            return Message.createBySuccess();
        }
        return Message.createByError();
    }

    @DeleteMapping("/{id}")
    public Message delProduct(@PathVariable("id") Long id){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(id);
        productInfo.setState(1);
        int i = productInfoService.updateByKey(productInfo);
        if(i != 0){
            return Message.createBySuccess();
        }

        return Message.createByError("更新失败,找不到对应的商品,请刷新后重试");
    }
}
