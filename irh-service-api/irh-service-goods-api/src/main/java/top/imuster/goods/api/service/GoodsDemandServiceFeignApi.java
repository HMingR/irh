package top.imuster.goods.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.config.FeignConfig;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.service.hystrix.GoodsCategoryServiceFeignApiHystrix;

/**
 * @ClassName: GoodsDemandServiceFeignApi
 * @Description: GoodsDemandServiceFeignApi
 * @author: hmr
 * @date: 2020/2/7 17:15
 */
@FeignClient(value = "goods-service", configuration = FeignConfig.class, path = "/goods/feign/demand",fallback = GoodsCategoryServiceFeignApiHystrix.class)
public interface GoodsDemandServiceFeignApi {

    /**
     * @Author hmr
     * @Description 分页条件查询用户的需求
     * @Date: 2020/2/7 17:17
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductDemandInfo>>
     **/
    @PostMapping
    Message<Page<ProductDemandInfo>> list(@RequestBody Page<ProductDemandInfo> page);

    /**
     * @Author hmr
     * @Description 根据id删除用户发布的需求
     * @Date: 2020/2/7 17:25
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @DeleteMapping("/{id}")
    Message<String> deleteDemandById(@PathVariable("id") Long id);

    /**
     * @Author hmr
     * @Description 根据id获得demand信息
     * @Date: 2020/2/7 17:29
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.goods.api.pojo.ProductDemandInfo>
     **/
    @GetMapping("/{id}")
    Message<ProductDemandInfo> getDemandById(@PathVariable("id") Long id);
}
