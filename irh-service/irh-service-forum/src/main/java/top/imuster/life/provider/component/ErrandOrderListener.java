package top.imuster.life.provider.component;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.forum.api.pojo.ErrandInfo;
import top.imuster.forum.api.pojo.ErrandOrder;
import top.imuster.life.provider.service.ErrandInfoService;
import top.imuster.life.provider.service.ErrandOrderService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: OrderListener
 * @Description: 监听ERRAND队列的信息
 * @author: hmr
 * @date: 2020/2/12 10:48
 */
@Component
@Slf4j
public class ErrandOrderListener {

    @Resource
    ErrandOrderService errandOrderService;

    @Resource
    ErrandInfoService errandInfoService;

    @Autowired
    RedisTemplate redisTemplate;

    @RabbitListener(queues = "queue_info_errand")
    public void generateOrder(String msg){
        try{
            ErrandOrder order = new ObjectMapper().readValue(msg, ErrandOrder.class);
            Long errandId = order.getErrandId();
            boolean available = errandInfoService.isAvailable(errandId);
            redisTemplate.expire(String.valueOf(order.getOrderCode()), 1, TimeUnit.MINUTES);
            if(!available){
                //当需要将订单存入数据库的时候，如果当前跑腿已经被人抢走了，则将结果存放在redis中，并结束当前方法
                redisTemplate.opsForHash().put(GlobalConstant.IRH_LIFE_ERRAND_MAP, String.valueOf(order.getOrderCode()), 1);
                return;
            }
            //下单成功也将订单编号放入redis中
            redisTemplate.opsForHash().put(GlobalConstant.IRH_LIFE_ERRAND_MAP, String.valueOf(order.getOrderCode()), 2);
            order.setState(3);
            int i = errandOrderService.insertEntry(order);
            if(i == 1){
                ErrandInfo errandInfo = new ErrandInfo();
                errandInfo.setId(errandId);
                errandInfo.setState(3);
                int flag = errandInfoService.updateByKey(errandInfo);

                //当更新成功之后将errandInfo的id存储到redis中，并设置过期时间
                if(flag == 1) redisTemplate.opsForHash().put(GlobalConstant.IRH_LIFE_ERRAND_MAP, RedisUtil.getErrandKey(errandId), errandId);
                redisTemplate.expire(RedisUtil.getErrandKey(errandId), 1, TimeUnit.MINUTES);
            }
        }catch (Exception e){
            log.error("解析从消息队列中得到的信息失败,错误信息为{},消息内容为{}",e.getMessage(), msg);
        }
    }

}
