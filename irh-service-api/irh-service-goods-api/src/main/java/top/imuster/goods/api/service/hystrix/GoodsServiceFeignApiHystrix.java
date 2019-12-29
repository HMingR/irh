package top.imuster.goods.api.service.hystrix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.dto.ProductInfoDto;
import top.imuster.goods.api.service.GoodsServiceFeignApi;

/**
 * @ClassName: GoodsServiceFeignApiHystrix
 * @Description: GoodsServiceFeignApi服务降级策略
 * @author: hmr
 * @date: 2019/12/29 21:56
 */
@Component
@Slf4j
public class GoodsServiceFeignApiHystrix implements GoodsServiceFeignApi {
    @Override
    public Message list(ProductInfoDto productInfoDto) {
        log.error("二手商品查询列表服务降级");
        return Message.createByError("当前网络繁忙,请稍后再试");
    }

    @Override
    public Message delProduct(Long id) {
        log.error("二手商品删除服务降级");
        return Message.createByError("当前网络繁忙,请稍后再试");
    }

    @Override
    public boolean lockStock(Long productId) {
        log.error("商品下单锁定库存失败服务降级");
        return false;
    }
}
