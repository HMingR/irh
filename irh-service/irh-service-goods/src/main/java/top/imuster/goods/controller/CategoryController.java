package top.imuster.goods.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.goods.api.pojo.ProductCategoryInfo;
import top.imuster.goods.config.GoodsException;
import top.imuster.goods.service.ProductCategoryInfoService;
import top.imuster.user.api.service.TestCustomerServiceFeign;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: CategoryController
 * @Description: 商品分类的controller
 * @author: hmr
 * @date: 2019/12/22 11:03
 */
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @Resource
    private TestCustomerServiceFeign testCustomerServiceFeign;

    @GetMapping("/test")
    public void test(){
        testCustomerServiceFeign.test();
    }

    @Resource
    ProductCategoryInfoService productCategoryInfoService;


    /**
     * @Description: 获得分类的树形结构数据
     * @Author: hmr
     * @Date: 2019/12/22 15:03
     * @param
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("获得分类的树形结构数据")
    @GetMapping("/list")
    public Message list() throws GoodsException {
        List<ProductCategoryInfo> categoryTree = productCategoryInfoService.getCategoryTree();
        return Message.createBySuccess(categoryTree);
    }

    /**
     * @Description: 添加分类
     * @Author: hmr
     * @Date: 2019/12/22 15:06
     * @param productCategoryInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PutMapping("/")
    public Message addCategory(@RequestBody @Validated(ValidateGroup.addGroup.class) ProductCategoryInfo productCategoryInfo, BindingResult bindingResult) throws GoodsException {
        validData(bindingResult);
        try{
            productCategoryInfoService.insertEntry(productCategoryInfo);
            return Message.createBySuccess();
        }catch (Exception e){
            throw new GoodsException("添加商品分类失败");
        }
    }

    /**
     * @Description: 删除商品分类
     * @Author: hmr
     * @Date: 2019/12/22 15:50
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("删除商品分类,再删除分类的时候需要提示用户会更新所有该分类下的商品分类为其父节点")
    @DeleteMapping("/{id}")
    public Message delCategory(@PathVariable("id") Long id) throws GoodsException{
        Integer integer = productCategoryInfoService.delCategoryById(id);
        return Message.createBySuccess("删除成功,更新了" + integer + "个商品的分类");
    }

    /**
     * @Description: 修改数据时的数据回显
     * @Author: hmr
     * @Date: 2019/12/22 15:53
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("修改数据时的数据回显")
    @GetMapping("/toEdit/{id}")
    public Message toEdit(@PathVariable("id") Long id){
        ProductCategoryInfo productCategoryInfo = productCategoryInfoService.selectEntryList(id).get(0);
        return Message.createBySuccess(productCategoryInfo);
    }

    @ApiOperation("修改分类")
    @PostMapping("/edit")
    public Message editCategory(@RequestBody @Validated(ValidateGroup.editGroup.class) ProductCategoryInfo productCategoryInfo, BindingResult bindingResult) throws GoodsException {
        validData(bindingResult);
        try{
            int i = productCategoryInfoService.updateByKey(productCategoryInfo);
            if(i != 0){
                return Message.createBySuccess("更新成功");
            }
        }catch (Exception e){
            logger.error("更新商品分类失败",e.getMessage(), e, productCategoryInfo);
            throw new GoodsException("更新失败");
        }
        return Message.createByError("更新失败");
    }
}
