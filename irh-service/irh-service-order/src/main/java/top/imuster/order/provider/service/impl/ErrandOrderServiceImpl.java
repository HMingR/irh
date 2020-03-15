package top.imuster.order.provider.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.config.RabbitMqConfig;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.common.core.utils.UuidUtils;
import top.imuster.order.api.pojo.ErrandOrder;
import top.imuster.order.provider.dao.ErrandOrderDao;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.ErrandOrderService;

import javax.annotation.Resource;

/**
 * ErrandOrderService 实现类
 * @author 黄明人
 * @since 2020-02-11 17:49:36
 */
@Service("errandOrderService")
@Slf4j
public class ErrandOrderServiceImpl extends BaseServiceImpl<ErrandOrder, Long> implements ErrandOrderService {

    @Resource
    private ErrandOrderDao errandOrderDao;

    @Autowired
    RabbitTemplate rabbitTemplate;
    
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public BaseDao<ErrandOrder, Long> getDao() {
        return this.errandOrderDao;
    }

    @Override
    public Message<String> delete(Long id, Long userId, Integer type) {
        ErrandOrder errandOrder = errandOrderDao.selectEntryList(id).get(0);
        if(errandOrder.getState() == 3) return Message.createByError("当前订单还没有完成,请等待订单完成之后再删除");

        //判断当前用户有没有权限删除
        if(type == 5){
            if(!errandOrder.getPublisherId().equals(userId)){
                log.error("id为{}的用户试图删除不是自己的跑腿订单", userId);
                return Message.createByError("非法操作,删除不属于自己的订单失败,多次执行该操作账号将被冻结");
            }
        }else if(type == 6){
            if(!errandOrder.getHolderId().equals(userId)){
                log.error("id为{}的用户试图删除不是自己的跑腿订单", userId);
                return Message.createByError("非法操作,删除不属于自己的订单失败,多次执行该操作账号将被冻结");
            }
        }
        errandOrder.setState(type);
        errandOrderDao.updateByKey(errandOrder);
        return Message.createBySuccess();
    }

    @Override
    public String receiveOrder(Long id, Long userId) throws JsonProcessingException {
        checkFromRedisById(id);
        ErrandOrder errandOrder = new ErrandOrder();
        String orderCode = String.valueOf(UuidUtils.nextId());
        errandOrder.setHolderId(userId);
        errandOrder.setErrandId(id);
        errandOrder.setOrderCode(orderCode);
        String value = new ObjectMapper().writeValueAsString(errandOrder);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM, MqTypeEnum.ERRAND.getRoutingKey(), value);
        return orderCode;
    }

    @Override
    public Message<String> getOrderStateByCode(String code) {
        String res = String.valueOf(redisTemplate.opsForHash().get(GlobalConstant.IRH_LIFE_ERRAND_MAP, code));
        if(res == null) res = "";
        if(res.equals("1")){
            return Message.createByError("接单失败,订单已经被被人抢走了");
        }else if(res.equals("2")){
            return Message.createBySuccess("接单成功,请按照发布者的要求及时完成任务");
        } else {
            Integer state = errandOrderDao.selectOrderStateByCode(code);
            if(state == null) return Message.createByError("接单失败,订单已经被被人抢走了");
            if(state == 3) return Message.createBySuccess("接单成功,请按照发布者的要求及时完成任务");
            return Message.createByError("接单失败,当前订单已经被其他人抢走了");
        }
    }


    /**
     * @Author hmr
     * @Description 从redis中查看当前的跑腿是否已经被接单
     * @Date: 2020/2/12 11:39
     * @param id
     * @reture: void
     **/
    private void checkFromRedisById(Long id){
        Boolean flag = redisTemplate.opsForHash().hasKey(GlobalConstant.IRH_LIFE_ERRAND_MAP, RedisUtil.getErrandKey(id));
        if(flag) throw new OrderException("下单失败,该订单已经被抢走了");
    }
}