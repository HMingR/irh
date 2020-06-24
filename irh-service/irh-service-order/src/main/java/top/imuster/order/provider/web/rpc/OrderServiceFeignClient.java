package top.imuster.order.provider.web.rpc;

import org.springframework.web.bind.annotation.*;
import top.imuster.order.api.pojo.ProductEvaluateInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;
import top.imuster.order.provider.service.ProductEvaluateInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: OrderServuceFeignClient
 * @Description:
 * @author: hmr
 * @date: 2019/12/27 15:33
 */
@RestController
@RequestMapping("/feign/order")
public class OrderServiceFeignClient implements OrderServiceFeignApi {

    @Resource
    ProductEvaluateInfoService productEvaluateInfoService;

    @Override
    @DeleteMapping("/{targetId}")
    public void deleteProductEvaluate(@PathVariable("targetId") Long targetId) {
        ProductEvaluateInfo condition = new ProductEvaluateInfo();
        condition.setId(targetId);
        condition.setState(1);
        productEvaluateInfoService.updateByKey(condition);
    }

    @Override
    @GetMapping("/evaluate/{id}")
    public Long getEvaluateWriterIdById(@PathVariable("id") Long targetId) {
        return null;
    }
}
