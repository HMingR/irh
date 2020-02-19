package top.imuster.goods.api.service;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.config.FeignConfig;
import top.imuster.goods.api.pojo.ProductCategoryInfo;
import top.imuster.goods.api.service.hystrix.GoodsCategoryServiceFeignApiHystrix;

import java.util.List;

/**
 * @ClassName: GoodsCategoryServiceFeignApi
 * @Description: GoodsCategoryServiceFeignApi
 * @author: hmr
 * @date: 2020/2/7 14:56
 */
@FeignClient(value = "goods-service", configuration = FeignConfig.class, path = "/goods/feign/category",fallbackFactory = GoodsCategoryServiceFeignApiHystrix.class)
public interface GoodsCategoryServiceFeignApi {
    /**
     * @Author hmr
     * @Description 管理员获得商品分类的树形结构
     * @Date: 2020/2/7 14:53
     * @param
     * @reture: void
     **/
    @GetMapping("/category")
    List<ProductCategoryInfo> adminCategoryTree();

    /**
     * @Description: 添加分类
     * @Author: hmr
     * @Date: 2019/12/22 15:06
     * @param productCategoryInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PutMapping
    Message addCategory(@RequestBody ProductCategoryInfo productCategoryInfo);


    /**
     * @Description: 删除商品分类
     * @Author: hmr
     * @Date: 2019/12/22 15:50
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @DeleteMapping("/{id}")
    Message delCategory(@PathVariable("id") Long id);

    /**
     * @Description: 根据id查询
     * @Author: hmr
     * @Date: 2019/12/22 15:53
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @GetMapping("/{id}")
    Message getInfoById(@PathVariable("id") Long id);

    @ApiOperation(value = "修改分类信息", httpMethod = "POST")
    @PostMapping("/edit")
    Message editCategory(@RequestBody ProductCategoryInfo productCategoryInfo);
}
