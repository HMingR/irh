package top.imuster.order.api.service.hystrix;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.imuster.order.api.service.OrderServiceFeignApi;

/**
 * @ClassName: OrderServiceFeignApiHystrix
 * @Description: 服务降级
 * @author: hmr
 * @date: 2019/12/29 22:02
 */
@Component
public class OrderServiceFeignApiHystrix implements FallbackFactory<OrderServiceFeignApi> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Override
    public OrderServiceFeignApi create(Throwable throwable) {
        log.error("OrderServiceFeignApiHystrix--->错误信息为{}",throwable.getMessage(), throwable);
        return new OrderServiceFeignApi() {
            @Override
            public void deleteProductEvaluate(Long targetId) {

            }

            @Override
            public Long getEvaluateWriterIdById(Long targetId) {
                return null;
            }
        };
    }
}
