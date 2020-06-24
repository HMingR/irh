package top.imuster.goods.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.goods.api.pojo.ProductCategoryInfo;
import top.imuster.goods.service.ProductCategoryInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: CategoryController
 * @Description: 商品分类的controller
 * @author: hmr
 * @date: 2019/12/22 11:03
 */
@Api("商品分类controller")
@RestController
@RequestMapping("/goods/category")
public class CategoryController extends BaseController {

    @Resource
    ProductCategoryInfoService productCategoryInfoService;

    @ApiOperation(value = "获得分类的树形结构数据", httpMethod = "GET")
    @GetMapping("/tree")
    public Message<List<ProductCategoryInfo>> adminCategoryTree() {
        return productCategoryInfoService.getCategoryTree();
    }
}
