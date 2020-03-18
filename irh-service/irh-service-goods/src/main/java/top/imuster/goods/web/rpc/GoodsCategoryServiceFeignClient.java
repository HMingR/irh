package top.imuster.goods.web.rpc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductCategoryInfo;
import top.imuster.goods.api.service.GoodsCategoryServiceFeignApi;
import top.imuster.goods.exception.GoodsException;
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
@RequestMapping("/goods/feign/category")
public class GoodsCategoryServiceFeignClient extends BaseController implements GoodsCategoryServiceFeignApi {

    @Resource
    ProductCategoryInfoService productCategoryInfoService;

    /**
     * @Description: 添加分类
     * @Author: hmr
     * @Date: 2019/12/22 15:06
     * @param productCategoryInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "添加分类", httpMethod = "PUT")
    @PutMapping
    public Message<String> addCategory(@RequestBody ProductCategoryInfo productCategoryInfo) throws GoodsException {
        productCategoryInfoService.insertEntry(productCategoryInfo);
        return Message.createBySuccess();
    }

    /**
     * @Description: 删除商品分类
     * @Author: hmr
     * @Date: 2019/12/22 15:50
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "删除商品分类,再删除分类的时候需要提示用户会更新所有该分类下的商品分类为其父节点", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Message<String> delCategory(@PathVariable("id") Long id) throws GoodsException{
        Integer integer = productCategoryInfoService.delCategoryById(id);
        return Message.createBySuccess("删除成功,更新了" + integer + "个商品的分类");
    }

    /**
     * @Description: 根据id查询
     * @Author: hmr
     * @Date: 2019/12/22 15:53
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "根据id查询", httpMethod = "GET")
    @GetMapping("/{id}")
    public Message<ProductCategoryInfo> getInfoById(@PathVariable("id") Long id){
        ProductCategoryInfo productCategoryInfo = productCategoryInfoService.selectEntryList(id).get(0);
        return Message.createBySuccess(productCategoryInfo);
    }

    @ApiOperation(value = "修改分类信息", httpMethod = "POST")
    @PostMapping("/edit")
    public Message<String> editCategory(@RequestBody ProductCategoryInfo productCategoryInfo) throws GoodsException {
        productCategoryInfoService.updateByKey(productCategoryInfo);
        return Message.createBySuccess("更新成功");
    }

    @Override
    @ApiOperation(value = "管理员获得分类的树形结构数据", httpMethod = "GET")
    @GetMapping("/category")
    public Message<List<ProductCategoryInfo>> adminCategoryTree() {
        return productCategoryInfoService.getCategoryTree();
    }
}
