package top.imuster.user.provider.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.user.provider.exception.UserException;

/**
 * @ClassName: AdminGoodsController
 * @Description: 管理员用来管理商品的控制器
 * @author: hmr
 * @date: 2020/1/9 11:10
 */
@RestController
@RequestMapping("/admin/goods")
@Api("管理员用来管理商品和商品下面的留言")
public class AdminGoodsController extends BaseController {

    @Autowired
    GoodsServiceFeignApi goodsServiceFeignApi;

    /**
     * @Description: 管理员根据id下架二手商品
     * @Author: hmr
     * @Date: 2019/12/27 15:10
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @DeleteMapping("/{id}")
    @ApiOperation(value = "管理员根据id下架二手商品", httpMethod = "DELETE")
    public Message delGoodsById(@PathVariable("id") @ApiParam("二手商品id") Long id) throws UserException {
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
    public Message goodsList(@RequestBody @ApiParam Page<ProductInfo> page){
        try{
            return goodsServiceFeignApi.list(page);
        }catch (Exception e){
            logger.error("远程调用goods模块出现异常{}", e.getMessage(), e);
            throw new UserException("远程调用goods模块出现异常" + e.getMessage());
        }
    }
}
