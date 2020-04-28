package top.imuster.order.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;
import top.imuster.order.api.service.hystrix.OrderServiceFeignApiHystrix;

import java.io.IOException;

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

    /**
     * @Author hmr
     * @Description 自动选择合适的订单出账
     * @Date: 2020/4/15 16:28
     * @param applyId
     * @param operatorId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @PostMapping("/grant/{operatorId}/{id}")
    Message<String> grantMoney(@PathVariable("id") Long applyId, @PathVariable("operatorId") Long operatorId) throws JsonProcessingException;


    /**
     * @Author hmr
     * @Description 确定出账
     * @Date: 2020/4/15 16:28
     * @param applyId
     * @param operatorId
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @GetMapping("/determine/{operatorId}/{applyId}")
    Message<String> determineGrant(@PathVariable("applyId") Long applyId, @PathVariable("operatorId") Long operatorId) throws IOException;

    /**
     * @Author hmr
     * @Description 根据id获得申请详情
     * @Date: 2020/4/28 10:38
     * @param type 5-标识查看的申请已经完成，返回的信息中需要包含使用了哪些人的订单   type的取值和实体类中的state一致
     * @param targetId 申请id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.order.api.pojo.ProductDonationApplyInfo>
     **/
    @GetMapping("/donation/{state}/{targetId}")
    Message<ProductDonationApplyInfo> getApplyInfoById(@PathVariable("state") Integer state, @PathVariable("targetId")Long targetId);

    /**
     * @Author hmr
     * @Description 条件查询申请
     * @Date: 2020/4/28 10:24
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.order.api.pojo.ProductDonationApplyInfo>>
     **/
    @PostMapping("/list")
    Message<Page<ProductDonationApplyInfo>> getApplyList(@RequestBody Page<ProductDonationApplyInfo> page);


}
