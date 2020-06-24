package top.imuster.goods.api.service.hystrix;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.service.GoodsDemandServiceFeignApi;

/**
 * @ClassName: GoodsDemandServiceFeignApiHystrix
 * @Description: GoodsDemandServiceFeignApiHystrix
 * @author: hmr
 * @date: 2020/2/7 17:16
 */
@Component
public class GoodsDemandServiceFeignApiHystrix implements FallbackFactory<GoodsDemandServiceFeignApi> {

    @Override
    public GoodsDemandServiceFeignApi create(Throwable throwable) {
        return new GoodsDemandServiceFeignApi() {
            @Override
            public Message<ProductDemandInfo> getDemandById(Long id) {
                return null;
            }
        };
    }
}
