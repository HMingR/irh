package top.imuster.goods.api.service.hystrix;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class GoodsDemandServiceFeignApiHystrix implements FallbackFactory<GoodsDemandServiceFeignApi> {
    /*@Override
    public Message<Page<ProductDemandInfo>> list(Page<ProductDemandInfo> page) {
        log.error("远程调用goods模块分页条件查询demand失败,page对象为{}", page);
        return null;
    }

    @Override
    public Message<String> deleteDemandById(Long id) {
        return null;
    }

    @Override
    public Message<ProductDemandInfo> getDemandById(Long id) {
        return null;
    }*/

    @Override
    public GoodsDemandServiceFeignApi create(Throwable throwable) {
        return null;
    }
}
