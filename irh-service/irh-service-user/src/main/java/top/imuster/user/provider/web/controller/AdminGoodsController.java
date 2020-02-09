package top.imuster.user.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.goods.api.pojo.ProductCategoryInfo;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsCategoryServiceFeignApi;
import top.imuster.goods.api.service.GoodsDemandServiceFeignApi;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.user.provider.exception.UserException;

import java.util.List;

/**
 * @ClassName: AdminGoodsController
 * @Description: 管理员用来管理商品的控制器
 * @author: hmr
 * @date: 2020/1/9 11:10
 */
@RestController
@RequestMapping("/admin/goods")
@Api("管理员用来管理商城模块")
public class AdminGoodsController extends BaseController {

    @Autowired
    GoodsServiceFeignApi goodsServiceFeignApi;

    @Autowired
    GoodsCategoryServiceFeignApi goodsCategoryServiceFeignApi;

    @Autowired
    GoodsDemandServiceFeignApi goodsDemandServiceFeignApi;

    /**
     * @Author hmr
     * @Description 分页条件查询用户商品需求
     * @Date: 2020/2/7 17:24
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductDemandInfo>>
     **/
    @ApiOperation(value = "分页条件查询用户商品需求", httpMethod = "POST")
    @PostMapping("/demand")
    public Message<Page<ProductDemandInfo>> demandList(@RequestBody  Page<ProductDemandInfo> page){
        ProductDemandInfo searchCondition = page.getSearchCondition();
        if(searchCondition == null){
            page.setSearchCondition(new ProductDemandInfo());
        }
        return goodsDemandServiceFeignApi.list(page);
    }

    @ApiOperation(value = "根据id删除用户发布的需求", httpMethod = "DELETE")
    @DeleteMapping("/demand/{id}")
    public Message<String> deleteDemandById(@PathVariable("id") Long id){
        return goodsDemandServiceFeignApi.deleteDemandById(id);
    }

    @ApiOperation("根据id获得需求信息")
    @GetMapping("/demand/{id}")
    public Message<ProductDemandInfo> getDemandInfoById(@PathVariable("id") Long id){
        return goodsDemandServiceFeignApi.getDemandById(id);
    }

    /**
     * @Description: 管理员根据id下架二手商品
     * @Author: hmr
     * @Date: 2019/12/27 15:10
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @DeleteMapping("/{id}")
    @ApiOperation(value = "管理员根据id下架二手商品", httpMethod = "DELETE")
    public Message<String> delGoodsById(@PathVariable("id") @ApiParam("二手商品id") Long id) throws UserException {
        return goodsServiceFeignApi.delProduct(id);
    }

    /**
     * @Description: 管理二手商品，按条件分页查询
     * @Author: hmr
     * @Date: 2019/12/27 12:07
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "查看二手商品，按条件分页查询", httpMethod = "POST")
    @PostMapping("/es")
    public Message<Page<ProductInfo>> goodsList(@RequestBody Page<ProductInfo> page){
        return goodsServiceFeignApi.list(page);
    }


    /**
     * @Author hmr
     * @Description 获得商城中商品分类的树形结构
     * @Date: 2020/2/7 15:12
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<top.imuster.goods.api.pojo.ProductCategoryInfo>>
     **/
    @ApiOperation("获得商城中商品分类的树形结构")
    @GetMapping("/category")
    public Message<List<ProductCategoryInfo>> tree(){
        List<ProductCategoryInfo> categoryInfos = goodsCategoryServiceFeignApi.adminCategoryTree();
        return Message.createBySuccess(categoryInfos);
    }

    /**
     * @Author hmr
     * @Description 添加商城商品分类信息
     * @Date: 2020/2/7 15:12
     * @param productCategoryInfo
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation(value = "添加商城商品分类信息", httpMethod = "POST")
    @PostMapping("/category")
    public Message<String> addCategory(@RequestBody ProductCategoryInfo productCategoryInfo, BindingResult bindingResult){
        validData(bindingResult);
        return goodsCategoryServiceFeignApi.addCategory(productCategoryInfo);
    }

    @ApiOperation(value = "修改分类信息", httpMethod = "PUT")
    @PutMapping("/category")
    public Message<String> editCategory(@RequestBody ProductCategoryInfo productCategoryInfo){
        goodsCategoryServiceFeignApi.editCategory(productCategoryInfo);
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 根据id获得商品分类信息
     * @Date: 2020/2/7 15:12
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.goods.api.pojo.ProductCategoryInfo>
     **/
    @ApiOperation(value = "根据id获得商品分类信息", httpMethod = "GET")
    @GetMapping("/category/{id}")
    public Message<ProductCategoryInfo> getInfoById(@PathVariable("id") Long id){
        return goodsCategoryServiceFeignApi.getInfoById(id);
    }

    /**
     * @Author hmr
     * @Description 根据id删除商品分类信息
     * @Date: 2020/2/7 15:12
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("根据id删除商品分类信息")
    @DeleteMapping("/category/{id}")
    public Message<String> deleteById(@PathVariable("id") Long id){
        return goodsCategoryServiceFeignApi.delCategory(id);
    }
}
