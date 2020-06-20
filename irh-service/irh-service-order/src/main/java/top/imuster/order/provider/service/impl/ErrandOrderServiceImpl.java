package top.imuster.order.provider.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.config.MessageCode;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.config.RabbitMqConfig;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.common.core.utils.UuidUtils;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.life.api.pojo.ErrandInfo;
import top.imuster.order.api.pojo.ErrandOrderEvaluateInfo;
import top.imuster.order.api.pojo.ErrandOrderInfo;
import top.imuster.order.provider.dao.ErrandOrderDao;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.ErrandOrderEvaluateInfoService;
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

    @Resource
    ErrandOrderEvaluateInfoService errandOrderEvaluateInfoService;


    @Override
    public BaseDao<ErrandOrderInfo, Long> getDao() {
        return this.errandOrderDao;
    }

    @Override
    @CacheEvict(value = GlobalConstant.IRH_FIVE_MINUTES_CACHE_KEY, key = " #userId + '::errandOrderList*'")
    public Message<String> delete(Long id, Long userId, Integer type) {
        List<ErrandOrderInfo> errandOrderInfos = errandOrderDao.selectEntryList(id);
        if(errandOrderInfos == null || errandOrderInfos.isEmpty()) return Message.createByError("未找到相关订单,请刷新后重试");
        ErrandOrderInfo errandOrderInfo = errandOrderInfos.get(0);
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
        String errandKey = RedisUtil.getErrandKey(id);
        Boolean hasKey = redisTemplate.hasKey(errandKey);
        if(hasKey != null && hasKey) return Message.createByError("订单已经被抢走了,请刷新后重新选择其他的");
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
        errandOrderInfo.setRequirement(errandInfo.getRequirement());

        String value = new ObjectMapper().writeValueAsString(errandOrderInfo);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPICS_INFORM, MqTypeEnum.ERRAND.getRoutingKey(), value);
        return Message.createBySuccess(orderCode);
    }

    @Override
    public Message<String> getOrderStateByCode(String code, Long targetId) {
        String redisKey = RedisUtil.getErrandOrderAvaliableMapKey(targetId);
        Boolean available = (Boolean) redisTemplate.opsForHash().get(redisKey, code);
        if(available == null) return Message.createByCustom(MessageCode.WAIT);
        if(available){
            return Message.createByError("接单失败,订单已经被被人抢走了");
        }else{
            return Message.createBySuccess("接单成功,请按照发布者的要求及时完成任务");
        }
    }

    @Override
    @CacheEvict(value = GlobalConstant.IRH_FIVE_MINUTES_CACHE_KEY, key = " #order.holderId + '::errandOrderList*'")
    public ErrandOrderInfo acceptErrand(ErrandOrderInfo order) {
        errandOrderDao.insertEntry(order);
        return order;
    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_FIVE_MINUTES_CACHE_KEY, key = "'errandOrderDetail::' + #id")
    public Message<ErrandOrderInfo> getOrderInfoById(Long id, Long userId) {
        List<ErrandOrderInfo> res = this.selectEntryList(id);
        if(res == null || res.isEmpty()) return Message.createBySuccess("未找到相关订单,请刷新后重试");
        ErrandOrderInfo orderInfo = res.get(0);
        if(!orderInfo.getPublisherId().equals(userId) && !orderInfo.getHolderId().equals(userId)){
            log.error("--------->用户编号为{}的用户试图访问编号为{}跑腿订单,该订单不属于他", userId, id);
            return Message.createByError("当前订单和你无关,请不要访问他人的订单");
        }
        return Message.createBySuccess(orderInfo);
    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_FIVE_MINUTES_CACHE_KEY, key = "#userId + ':errandOrderList::type:' + #type + ':state:' + #state + ':page:' + #currentPage")
    public Message<Page<ErrandOrderInfo>> list(Integer pageSize, Integer currentPage, Integer type, Integer state, Long userId) {
        ErrandOrderInfo searchCondition = new ErrandOrderInfo();
        searchCondition.setOrderField("create_time");
        searchCondition.setOrderFieldType("DESC");
        searchCondition.setState(state);
        if(type == 1) searchCondition.setPublisherId(userId);
        if(type == 2) searchCondition.setHolderId(userId);
        Page<ErrandOrderInfo> page = new Page<>();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        page = this.selectPage(searchCondition, page);
        return Message.createBySuccess(page);
    }

    @Override
    @CacheEvict(value = GlobalConstant.IRH_FIVE_MINUTES_CACHE_KEY, key = " #userId + '::errandOrderList*'")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Message<String> finishOrder(Long userId, Long errandOrderId, ErrandOrderEvaluateInfo evaluateInfo) {
        List<ErrandOrderInfo> res = errandOrderDao.selectEntryList(errandOrderId);
        if(res == null || res.isEmpty()) return Message.createByError("未找到相关订单");
        ErrandOrderInfo errandOrderInfo = res.get(0);
        if(!errandOrderInfo.getPublisherId().equals(userId)){
            log.error("订单的发布者和当前用户不一致,订单id为{},当前用户id为{}",errandOrderId, userId);
            throw new OrderException("非法操作,恶意篡改登录信息,如非恶意请重新登录");
        }
        Long errandId = errandOrderInfo.getErrandId();
        boolean b = goodsServiceFeignApi.updateErrandInfoById(errandId, 4);
        if(!b) throw new OrderException("更新失败,请稍后重试");

        errandOrderInfo.setState(4);
        errandOrderInfo.setFinishTime(DateUtil.now());
        errandOrderDao.updateByKey(errandOrderInfo);

        errandOrderEvaluateInfoService.writeEvaluate(evaluateInfo);
        return Message.createBySuccess();
    }

}