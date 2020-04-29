package top.imuster.order.provider.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.life.api.service.ForumServiceFeignApi;
import top.imuster.order.api.pojo.ErrandOrderInfo;
import top.imuster.order.provider.service.ErrandOrderService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: OrderListener
 * @Description: 监听ERRAND队列的信息
 * @author: hmr
 * @date: 2020/2/12 10:48
 */
@Component
public class ErrandOrderListener {


    private static final Logger log = LoggerFactory.getLogger(ErrandOrderListener.class);

    @Resource
    ErrandOrderService errandOrderService;

    @Autowired
    ForumServiceFeignApi forumServiceFeignApi;

    @Autowired
    RedisTemplate redisTemplate;

    @RabbitListener(queues = "queue_info_errand")
    public void generateOrder(String msg){
        try{
            ErrandOrderInfo order = new ObjectMapper().readValue(msg, ErrandOrderInfo.class);
            Long errandId = order.getErrandId();
            Integer errandVersion = order.getErrandVersion();
            boolean available = forumServiceFeignApi.errandIsAvailable(errandId, errandVersion);
            redisTemplate.expire(String.valueOf(order.getOrderCode()), 1, TimeUnit.MINUTES);
            if(!available){
                //当需要将订单存入数据库的时候，如果当前跑腿已经被人抢走了，则将结果存放在redis中，并结束当前方法
                redisTemplate.opsForHash().put(GlobalConstant.IRH_LIFE_ERRAND_MAP, String.valueOf(order.getOrderCode()), 1);
                return;
            }
            //下单成功也将订单编号放入redis中
            redisTemplate.opsForHash().put(GlobalConstant.IRH_LIFE_ERRAND_MAP, String.valueOf(order.getOrderCode()), 2);
            boolean flag = forumServiceFeignApi.updateErrandInfoById(errandId, errandVersion);
            if(flag){
                order.setState(3);
                errandOrderService.acceptErrand(order);
                //当更新成功之后将errandInfo的id存储到redis中，并设置过期时间
                redisTemplate.opsForHash().put(GlobalConstant.IRH_LIFE_ERRAND_MAP, RedisUtil.getErrandKey(errandId), errandId);
                redisTemplate.expire(RedisUtil.getErrandKey(errandId), 1, TimeUnit.MINUTES);
            }
        }catch (Exception e){
            log.error("解析从消息队列中得到的信息失败,错误信息为{},消息内容为{}",e.getMessage(), msg);
        }
    }

}
