package top.imuster.goods.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.BrowserAnnotation;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.goods.api.pojo.ProductRotationImgInfo;
import top.imuster.goods.service.ProductRotationImgInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: ProductRotationController
 * @Description: ProductRotationController
 * @author: hmr
 * @date: 2020/6/1 15:51
 */
@RestController
@RequestMapping("/rotation")
public class ProductRotationController {

    @Resource
    ProductRotationImgInfoService productRotationImgInfoService;

    @GetMapping("/{pageSize}/{currentPage}")
    public Message<Page<ProductRotationImgInfo>> list(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        Page<ProductRotationImgInfo> page = new Page<>();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        ProductRotationImgInfo condition = new ProductRotationImgInfo();
        condition.setState(2);
        condition.setOrderField("click_total");
        condition.setOrderFieldType("DESC");
        page = productRotationImgInfoService.selectPage(condition, page);
        return Message.createBySuccess(page);
    }

    @BrowserAnnotation(browserType = BrowserType.PRODUCT_ROTATION, value = "#p0")
    @GetMapping("/browse/{id}")
    public void browse(@PathVariable("id") Long id){

    }

}
