package top.imuster.order.provider.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.order.provider.service.OrderInfoService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName: OrderKeyExpirationListener
 * @Description: OrderKeyExpirationListener 订单过期的事件的执行器
 * @author: hmr
 * @date: 2020/5/12 15:10
 */
@Component
public class OrderKeyExpirationListener extends KeyExpirationEventMessageListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    OrderInfoService orderInfoService;

    @Autowired
    GoodsServiceFeignApi goodsServiceFeignApi;

    public OrderKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        log.info("-------------->redis的key失效{}", expiredKey);
        if(expiredKey.startsWith(GlobalConstant.IRH_ORDER_CODE_EXPIRE_KEY)){
            String[] split = expiredKey.split(GlobalConstant.IRH_ORDER_CODE_EXPIRE_KEY);
            String orderCode = split[1];
            Map<String, String> map = orderInfoService.getProductIdByExpireOrderCode(orderCode);
            if(map == null) return;
//            Integer i = orderInfoService.cancleOrderByCode(String.valueOf(orderCode), Long.parseLong(map.get("buyerId")), Long.parseLong(map.get("id")));
            orderInfoService.cancelOrder(Long.parseLong(map.get("id")), Long.parseLong(map.get("buyerId")), 4);
//            if(i == 1){
//              goodsServiceFeignApi.updateProductState(Long.parseLong(map.get("productId")), 2);
//            }
        }
    }
}
