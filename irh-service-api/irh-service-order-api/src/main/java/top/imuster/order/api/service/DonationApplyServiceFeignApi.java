package top.imuster.order.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;
import top.imuster.order.api.service.hystrix.OrderServiceFeignApiHystrix;

/**
 * @ClassName: DonationApplyServiceFeignApi
 * @Description: DonationApplyServiceFeignApi
 * @author: hmr
 * @date: 2020/4/14 17:15
 */
@FeignClient(value = "order-service", path = "/feign/order/donation", fallbackFactory = OrderServiceFeignApiHystrix.class)
public interface DonationApplyServiceFeignApi {

    /**
     * @Author hmr
     * @Description 申请
     * @Date: 2020/4/14 17:17
     * @param applyInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @PostMapping
    Message<String> apply(@RequestBody ProductDonationApplyInfo applyInfo);

    /**
     * @Author hmr
     * @Description 审核
     * @Date: 2020/4/14 17:18
     * @param approveInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @PostMapping("/approve")
    Message<String> approve(@RequestBody ProductDonationApplyInfo approveInfo);

}
