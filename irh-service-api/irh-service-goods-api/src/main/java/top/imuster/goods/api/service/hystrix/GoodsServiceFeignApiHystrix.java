package top.imuster.goods.api.service.hystrix;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.life.api.pojo.ErrandInfo;

/**
 * @ClassName: GoodsServiceFeignApiHystrix
 * @Description: GoodsServiceFeignApi服务降级策略
 * @author: hmr
 * @date: 2019/12/29 21:56
 */
@Component
public class GoodsServiceFeignApiHystrix implements FallbackFactory<GoodsServiceFeignApi> {

    private static final Logger log = LoggerFactory.getLogger(GoodsServiceFeignApiHystrix.class);

    @Override
    public GoodsServiceFeignApi create(Throwable throwable) {
        log.error("出现异常");
        return new GoodsServiceFeignApi() {
            @Override
            public Message<Page<ProductInfo>> list(Page<ProductInfo> page) {
                log.error("二手商品查询列表服务降级");
                return Message.createByError("当前网络繁忙,请稍后再试");
            }

            @Override
            public Message<String> delProduct(Long id) {
                log.error("二手商品删除服务降级");
                return Message.createByError("当前网络繁忙,请稍后再试");
            }

            @Override
            public ProductInfo lockStock(Long productId, Integer version) {
                return null;
            }

            @Override
            public boolean deleteProductMessageById(Long id) {
                log.error("删除商品留言失败，服务降级");
                return false;
            }

            @Override
            public Long getConsumerIdByType(Long id, Integer type) {
                log.error("根据商品id查询会员id失败，服务降级");
                return null;
            }

            @Override
            public ProductInfo getProductInfoByProductMessage(Long targetId) {
                log.error("根据商品留言信息查询商品信息失败,服务降级");
                return null;
            }

            @Override
            public boolean updateErrandInfoById(Long id, Integer errandVersion, Integer state) {
                return false;
            }

            @Override
            public boolean updateErrandInfoById(Long id, Integer state) {
                return false;
            }

            @Override
            public boolean errandIsAvailable(Long errandId, Integer errandVersion) {
                return false;
            }

            @Override
            public ErrandInfo getErrandInfoById(Long errandId) {
                return null;
            }

            @Override
            public ProductInfo getProductBriefInfoById(Long productId) {
                return null;
            }

            @Override
            public boolean updateProductState(Long productId, Integer state) {
                return false;
            }

            @Override
            public Integer getErrandVersionById(Long errandId) {
                return null;
            }

            @Override
            public boolean finishErrandPay(Long id) {
                return false;
            }
        };
    }
}
