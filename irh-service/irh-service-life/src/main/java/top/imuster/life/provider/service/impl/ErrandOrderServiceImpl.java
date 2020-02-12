package top.imuster.life.provider.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import top.imuster.life.api.pojo.ErrandOrder;
import top.imuster.life.provider.dao.ErrandOrderDao;
import top.imuster.life.provider.exception.LifeException;
import top.imuster.life.provider.service.ErrandOrderService;

import javax.annotation.Resource;

/**
 * ErrandOrderService 实现类
 * @author 黄明人
 * @since 2020-02-11 17:49:36
 */
@Service("errandOrderService")
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
    public Message<String> delete(Long id, Long userId) {
        ErrandOrder errandOrder = errandOrderDao.selectEntryList(id).get(0);
        if(errandOrder.getState() == 3){
            return Message.createByError("当前订单还没有完成,请等待订单完成之后再删除");
        }
        errandOrder.setState(2);
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
        Integer state = errandOrderDao.selectOrderStateByCode(code);
        if(state == 3) return Message.createBySuccess();
        return Message.createByError("接单失败,当前订单已经被其他人抢走了");
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
        if(flag) throw new LifeException("下单失败,该订单已经被抢走了");
    }
}