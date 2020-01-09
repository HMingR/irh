package top.imuster.goods.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.config.FeignConfig;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.hystrix.GoodsServiceFeignApiHystrix;

/**
 * @ClassName: GoodsServiceFeignApi
 * @Description: goods模块提供给其他模块调用的结构
 * @author: hmr
 * @date: 2019/12/26 20:34
 */
@FeignClient(value = "goods-service", configuration = FeignConfig.class, path = "/goods/feign",fallback = GoodsServiceFeignApiHystrix.class)
public interface GoodsServiceFeignApi {

    /**
     * @Description: 分页条件查询二手商品
     * @Author: hmr
     * @Date: 2019/12/26 20:38
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping(value = "/es/list")
    Message list(@RequestBody Page<ProductInfo> page);

    /**
     * @Description: 提供给管理员的下架商品接口
     * @Author: hmr
     * @Date: 2019/12/27 15:12
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @DeleteMapping("/es/{id}")
    Message delProduct(@PathVariable("id") Long id);

    /**
     * @Description: 用户在下单的时候校验库存,如果有库存，则锁住库存.如果没有库存或者没有锁住库存，则返回false
     * @Author: hmr
     * @Date: 2019/12/28 10:58
     * @param productId
     * @reture: boolean
     **/
    @GetMapping("/es/lockStock/{productId}")
    boolean lockStock(@PathVariable("productId") Long productId);

    /**
     * @Description: 交易成功之后修改商品状态为下架
     * @Author: hmr
     * @Date: 2019/12/30 16:08
     * @param productId
     * @reture: boolean
     **/
    @GetMapping("/es/stockOut/{productId}")
    boolean productStockOut(@PathVariable("productId") Long productId);

}
