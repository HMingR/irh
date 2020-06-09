package top.imuster.goods.web.controller.admin;

import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.exception.GoodsException;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: ProductAdminController
 * @Description: ProductAdminController
 * @author: hmr
 * @date: 2020/6/9 14:14
 */
@RestController
@RequestMapping("/admin/es")
public class ProductAdminController {

    @Resource
    ProductInfoService productInfoService;

    @PostMapping(value = "/list")
    public Message<Page<ProductInfo>> list(@RequestBody Page<ProductInfo> page) {
        ProductInfo productInfo = page.getSearchCondition();
        Page<ProductInfo> productInfoPage = productInfoService.selectPage(productInfo, page);
        return Message.createBySuccess(productInfoPage);
    }

    
    @DeleteMapping("/{id}")
    public Message<String> delProduct(@PathVariable("id") Long id) throws GoodsException {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(id);
        productInfo.setState(1);
        int i = productInfoService.updateByKey(productInfo);
        if(i != 1){
            return Message.createByError("删除失败,未找到该记录,请刷新后重试");
        }
        return Message.createBySuccess("操作成功");
    }
}
