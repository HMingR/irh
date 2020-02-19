package top.imuster.goods.api.service.hystrix;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductCategoryInfo;
import top.imuster.goods.api.pojo.ProductEvaluateInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.pojo.ProductMessage;
import top.imuster.goods.api.service.GoodsServiceFeignApi;

import java.util.List;

/**
 * @ClassName: GoodsServiceFeignApiHystrix
 * @Description: GoodsServiceFeignApi服务降级策略
 * @author: hmr
 * @date: 2019/12/29 21:56
 */
@Component
@Slf4j
public class GoodsServiceFeignApiHystrix implements FallbackFactory<GoodsServiceFeignApi> {
/*@Override
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
    public boolean lockStock(Long productId) {
        log.error("商品下单锁定库存失败服务降级");
        return false;
    }

    @Override
    public boolean productStockOut(Long productId) {
        log.error("商品下单后改变商品状态失败，服务降级");
        return false;
    }

    @Override
    public boolean deleteProductMessageById(Long id) {
        log.error("删除商品留言失败，服务降级");
        return false;
    }

    @Override
    public boolean deleteProductEvaluate(Long id) {
        log.error("删除商品评价失败，服务降级");
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
    public ProductEvaluateInfo getProductEvaluateInfoByEvaluateId(Long targetId) {
        return null;
    }*/
    @Override
    public GoodsServiceFeignApi create(Throwable throwable) {
        log.error("出现异常");
        return null;
    }
}
