package top.imuster.order.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.imuster.order.api.service.hystrix.OrderServiceFeignApiHystrix;

/**
 * @ClassName: OrderServiceFeignApi
 * @Description: order模块提供给其他模块的接口
 * @author: hmr
 * @date: 2019/12/27 15:28
 */

@FeignClient(value = "order-service", path = "/feign/order", fallbackFactory = OrderServiceFeignApiHystrix.class)
public interface OrderServiceFeignApi {

    /**
     * @Author hmr
     * @Description 根据评价id删除商品评价
     * @Date: 2020/5/11 19:54
     * @param targetId
     * @reture: void
     **/
    @DeleteMapping("/{targetId}")
    void deleteProductEvaluate(@PathVariable("targetId") Long targetId);

    /**
     * @Author hmr
     * @Description 根据评价id查看评价人
     * @Date: 2020/5/11 19:57
     * @param targetId
     * @reture: java.lang.Long
     **/
    @GetMapping("/evaluate/{id}")
    Long getEvaluateWriterIdById(@PathVariable("id") Long targetId);
}
