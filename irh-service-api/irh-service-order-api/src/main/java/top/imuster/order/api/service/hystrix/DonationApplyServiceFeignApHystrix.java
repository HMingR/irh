package top.imuster.order.api.service.hystrix;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.dto.DonationApproveDto;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;
import top.imuster.order.api.service.DonationApplyServiceFeignApi;

/**
 * @ClassName: DonationApplyServiceFeignApHystrix
 * @Description: DonationApplyServiceFeignApHystrix
 * @author: hmr
 * @date: 2020/4/14 17:16
 */
@Component
public class DonationApplyServiceFeignApHystrix implements FallbackFactory<DonationApplyServiceFeignApi> {
    @Override
    public DonationApplyServiceFeignApi create(Throwable throwable) {
        return new DonationApplyServiceFeignApi() {
            @Override
            public Message<String> apply(ProductDonationApplyInfo applyInfo) {
                return null;
            }

            @Override
            public Message<String> approve(Long id, DonationApproveDto approveDto) {
                return null;
            }
        };
    }
}
