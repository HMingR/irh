package top.imuster.order.api.service.hystrix;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
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
            public Message<String> approve(ProductDonationApplyInfo approveInfo) {
                return null;
            }

            @Override
            public Message<String> grantMoney(Long applyId, Long operatorId) {
                return null;
            }

            @Override
            public Message<String> determineGrant(Long applyId, Long operatorId) {
                return null;
            }

            @Override
            public Message<ProductDonationApplyInfo> getApplyInfoById(Integer state, Long targetId) {
                return null;
            }

            @Override
            public Message<Page<ProductDonationApplyInfo>> getApplyList(Page<ProductDonationApplyInfo> page) {
                return null;
            }
        };
    }
}
