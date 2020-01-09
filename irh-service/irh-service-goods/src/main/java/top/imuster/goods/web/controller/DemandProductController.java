package top.imuster.goods.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductInfo;

/**
 * @ClassName: DemandProductController
 * @Description: 二手商城中提供给用户发布用户想要的商品
 * @author: hmr
 * @date: 2019/12/31 20:35
 */
@RestController
@RequestMapping("/goods/demand")
public class DemandProductController {

    @ApiOperation("条件查询")
    @PostMapping("/list")
    public Message list(Page<ProductInfo> page){
        return null;
    }

    @ApiOperation("发布需求")
    @PutMapping
    public Message add(){
        return null;
    }

    @PostMapping
    public Message edit(ProductInfo productInfo){
        return null;
    }

    @ApiOperation("删除用户发布的需求")
    @DeleteMapping("/{productId}")
    public Message del(@PathVariable("productId") Long id){
        return null;
    }
}
