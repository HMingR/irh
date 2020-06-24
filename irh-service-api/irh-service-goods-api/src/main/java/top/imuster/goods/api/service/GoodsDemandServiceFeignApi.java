package top.imuster.goods.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.config.FeignConfig;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.service.hystrix.GoodsDemandServiceFeignApiHystrix;

/**
 * @ClassName: GoodsDemandServiceFeignApi
 * @Description: GoodsDemandServiceFeignApi
 * @author: hmr
 * @date: 2020/2/7 17:15
 */
@FeignClient(value = "goods-service", configuration = FeignConfig.class, path = "/goods/feign/demand",fallbackFactory = GoodsDemandServiceFeignApiHystrix.class)
public interface GoodsDemandServiceFeignApi {

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
