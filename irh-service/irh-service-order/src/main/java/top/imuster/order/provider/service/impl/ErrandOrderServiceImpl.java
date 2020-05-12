package top.imuster.order.provider.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.life.api.pojo.ErrandInfo;
import top.imuster.order.api.pojo.ErrandOrderInfo;
import top.imuster.order.provider.dao.ErrandOrderDao;
import top.imuster.order.provider.service.ErrandOrderService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ErrandOrderService 实现类
 * @author 黄明人
 * @since 2020-02-11 17:49:36
 */
@Service("errandOrderService")
public class ErrandOrderServiceImpl extends BaseServiceImpl<ErrandOrderInfo, Long> implements ErrandOrderService {

    private static final Logger log = LoggerFactory.getLogger(ErrandOrderServiceImpl.class);


    @Resource
    private ErrandOrderDao errandOrderDao;

    @Autowired
    RabbitTemplate rabbitTemplate;
    
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    GoodsServiceFeignApi goodsServiceFeignApi;


    @Override
    public BaseDao<ErrandOrderInfo, Long> getDao() {
        return this.errandOrderDao;
    }

    @Override
    public Message<String> delete(Long id, Long userId, Integer type) {
        ErrandOrderInfo errandOrderInfo = errandOrderDao.selectEntryList(id).get(0);
        if(errandOrderInfo.getState() == 3) return Message.createByError("当前订单还没有完成,请等待订单完成之后再删除");

        //判断当前用户有没有权限删除
        if(type == 5){
            if(!errandOrderInfo.getPublisherId().equals(userId)){
                log.error("id为{}的用户试图删除不是自己的跑腿订单", userId);
                return Message.createByError("非法操作,删除不属于自己的订单失败,多次执行该操作账号将被冻结");
            }
        }else if(type == 6){
            if(!errandOrderInfo.getHolderId().equals(userId)){
                log.error("id为{}的用户试图删除不是自己的跑腿订单", userId);
                return Message.createByError("非法操作,删除不属于自己的订单失败,多次执行该操作账号将被冻结");
            }
        }
        errandOrderInfo.setState(type);
        errandOrderDao.updateByKey(errandOrderInfo);
        return Message.createBySuccess();
    }

    @Override
    public Message<String> receiveOrder(Long id, Integer version, Long userId) throws JsonProcessingException {
        boolean flag = redisTemplate.opsForHash().hasKey(GlobalConstant.IRH_LIFE_ERRAND_MAP, RedisUtil.getErrandKey(id));
        if(flag) return Message.createByError("订单已经被抢走了,请刷新后重新选择其他的");
        ErrandInfo errandInfo = goodsServiceFeignApi.getErrandInfoById(id);

        if(errandInfo == null) return Message.createByError("没有找到相关的跑腿信息,请刷新后重试");

        ErrandOrderInfo errandOrderInfo = new ErrandOrderInfo();

        String orderCode = String.valueOf(UuidUtils.nextId());
        errandOrderInfo.setPayMoney(errandInfo.getMoney());
        errandOrderInfo.setHolderId(userId);
        errandOrderInfo.setErrandId(id);
        errandOrderInfo.setPublisherId(errandInfo.getPublisherId());
        errandOrderInfo.setOrderCode(orderCode);
        errandOrderInfo.setErrandVersion(version);
        errandOrderInfo.setAddress(errandInfo.getAddress());
        errandOrderInfo.setPhoneNum(errandInfo.getPhoneNum());

        String value = new ObjectMapper().writeValueAsString(errandOrderInfo);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM, MqTypeEnum.ERRAND.getRoutingKey(), value);
        return Message.createBySuccess(orderCode);
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

    @Override
    public ErrandOrderInfo acceptErrand(ErrandOrderInfo order) {
        errandOrderDao.insertEntry(order);
        return order;
    }

    @Override
    public Message<ErrandOrderInfo> getOrderInfoById(Long id) {
        List<ErrandOrderInfo> res = this.selectEntryList(id);
        if(res == null || res.isEmpty()) return Message.createBySuccess("未找到相关订单,请刷新后重试");
        ErrandOrderInfo orderInfo = res.get(0);
        return Message.createBySuccess(orderInfo);
    }
}