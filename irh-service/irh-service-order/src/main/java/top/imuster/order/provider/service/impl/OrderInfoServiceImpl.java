package top.imuster.order.provider.service.impl;


import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.ReleaseAnnotation;
import top.imuster.common.core.dto.rabbitMq.SendOrderExpireDto;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.common.core.enums.OperationType;
import top.imuster.common.core.enums.ReleaseType;
import top.imuster.common.core.utils.*;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.order.api.dto.OrderTrendDto;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.dao.OrderInfoDao;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.OrderInfoService;
import top.imuster.order.provider.service.ProductEvaluateInfoService;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * OrderInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Slf4j
@Service("orderInfoService")
public class OrderInfoServiceImpl extends BaseServiceImpl<OrderInfo, Long> implements OrderInfoService {

    @Autowired
    GoodsServiceFeignApi goodsServiceFeignApi;

    @Resource
    private OrderInfoDao orderInfoDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GenerateSendMessageService generateSendMessageService;

    @Autowired
    ProductEvaluateInfoService productEvaluateInfoService;

    @Autowired
    private UserServiceFeignApi userServiceFeignApi;

    @Override
    public BaseDao<OrderInfo, Long> getDao() {
        return this.orderInfoDao;
    }

    @Override
    public OrderInfo getOrderInfoByOrderCode(String orderCode) {
        return orderInfoDao.selectOrderByOrderCode(orderCode);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "userProductOrderList::", key = "#userId + '*'")
    })
    public Message<OrderInfo> getOrderByProduct(OrderInfo orderInfo, Long userId, Long productId){
        String orderCode = orderInfo.getOrderCode();
        String orderCodeKey = RedisUtil.getOrderCodeKey(userId);
        String redisOrder = (String)redisTemplate.opsForValue().get(orderCodeKey);

        if( StringUtils.isEmpty(orderCode) || StringUtils.isEmpty(redisOrder) || !orderCode.equals(redisOrder)){
            throw new OrderException("错误的订单信息,请刷新页面重新提交订单");
        }
        redisTemplate.delete(orderCodeKey);

        String address = orderInfo.getAddress();
        String phoneNum = orderInfo.getPhoneNum();

        //校验地址和电话
        if(StringUtils.isEmpty(address) || StringUtils.isEmpty(phoneNum)){
            Map<String, String> addAndPhone = userServiceFeignApi.getUserAddressAndPhoneById(userId);
            if(StringUtils.isEmpty(address)){
                String originalAdd = addAndPhone.get("address");
                if(StringUtils.isEmpty(originalAdd)) throw new OrderException("您没有在个人中心中设置默认地址,也没有在提交订单的时候设置送货地址,请填写地址后重新提交");
                orderInfo.setAddress(originalAdd);
            }
            if(StringUtils.isEmpty(phoneNum)){
                String originalPhone = addAndPhone.get("phoneNum");
                if (StringUtils.isEmpty(originalPhone)) throw new OrderException("您没有在个人中心中完善您的联系电话或者没有在提交订单的时候提交联系电话");
                orderInfo.setPhoneNum(phoneNum);
            }
        }

        ProductInfo product = checkProduct(productId, orderInfo.getProductVersion());
        if(product == null){
            throw new OrderException("下单慢了哦,当前商品已经被别人抢走了");
        }

        orderInfo.setProductId(product.getId());
        orderInfo.setPaymentMoney(product.getSalePrice());
        orderInfo.setSalerId(product.getConsumerId());
        orderInfo.setBuyerId(userId);
        orderInfo.setState(40);
        orderInfo.setTradeType(orderInfo.getTradeType());

        orderInfoDao.insertOrder(orderInfo);
        Long orderId = orderInfo.getId();
        if(orderId == null){
            log.error("插入订单返回插入值的id为null,订单信息为{}", orderInfo);
            throw new OrderException("生成订单失败,请稍后重试");
        }

        //设置十分钟的过期时间
        SendOrderExpireDto sendOrderExpireDto = new SendOrderExpireDto();
        sendOrderExpireDto.setUserId(userId);
        sendOrderExpireDto.setOrderId(orderId);
        generateSendMessageService.sendDeadMsg(sendOrderExpireDto);
        return Message.createBySuccess(orderInfo);
    }

    @Override
    public Message<OrderTrendDto> getOrderAmountTrend(Integer type) {
        OrderTrendDto result = getResult(type, 1);
        return Message.createBySuccess(result);
    }

    @Override
    public Message<OrderTrendDto> getOrderTotalTrend(Integer type) {
        OrderTrendDto result = getResult(type, 2);
        return Message.createBySuccess(result);
    }


    @Override
    @Cacheable(value = "userProductOrderList::", key = "#userId + ':type:' + #type + ':page:' + #currentPage")
    public Message<Page<OrderInfo>> list(Integer pageSize, Integer currentPage, Long userId, Integer type) {
        Page<OrderInfo> page = new Page<>();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setStartIndex((currentPage - 1) * pageSize);
        orderInfo.setEndIndex(pageSize);
        orderInfo.setOrderField("create_time");
        orderInfo.setOrderFieldType("DESC");
        if(type == 2)orderInfo.setBuyerId(userId);  //买家
        else orderInfo.setSalerId(userId);    //卖家

        Integer count = orderInfoDao.selectOrderListCountByUserId(orderInfo);
        List<OrderInfo> list = orderInfoDao.selectOrderListByUserId(orderInfo);

        /*list.stream().forEach(condition -> {
            Long id = condition.getId();
            Long evaluateId = productEvaluateInfoService.getEvaluateIdByOrderId(id);
            condition.setEvaluateId(evaluateId);
        });*/
        page.setTotalCount(count);
        page.setData(list);
        return Message.createBySuccess(page);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userProductOrderList::", key = "#userId + '*'")
    })
    public Message<String> finishOrder(Long orderId, Long userId) {
        List<OrderInfo> orderInfos = this.selectEntryList(orderId);
        if(orderInfos == null || orderInfos.isEmpty()){
            return Message.createBySuccess("该订单不存在,请刷新后重试");
        }
        OrderInfo orderInfo = orderInfos.get(0);
        if(!orderInfo.getBuyerId().equals(userId)){
            log.info("----->编号为{}的用户试图又该不属于自己的订单状态,订单编号为{}",userId, orderInfo.getId());
            return Message.createByError("修改的订单不是本人的,属于非法操作,您当前的操作已经被记录");
        }
        orderInfo = new OrderInfo();
        orderInfo.setId(orderId);
        orderInfo.setState(50);
        orderInfo.setFinishTime(DateUtil.current());
        orderInfoDao.updateByKey(orderInfo);
        deleteFromES(orderInfo.getProductId());
        return Message.createBySuccess();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userProductOrderList::", key = "#userId + '*'")
    })
    public Boolean autoFinishOrder(Long orderId, Long userId){
        List<OrderInfo> orderInfos = this.selectEntryList(orderId);
        if(orderInfos == null || orderInfos.isEmpty()) return false;
        OrderInfo orderInfo = orderInfos.get(0);
        Integer state = orderInfo.getState();
        if(state != 45) return true;
        orderInfo.setState(50);
        orderInfo.setFinishTime(DateUtil.current());
        int i = orderInfoDao.updateByKey(orderInfo);
        if(i != 1) {
            log.error("自动完成订单,更新订单状态失败,订单编号为{}", orderId);
            return false;
        }
        SendUserCenterDto userCenterDto = new SendUserCenterDto();
        userCenterDto.setContent(new StringBuffer().append("您的编号为: ").append(orderInfo.getOrderCode()).append(" 的订单已经超过了完成时间,已经自动完成该订单,如果有任何疑问,请联系客服").toString());
        userCenterDto.setNewsType(70);
        userCenterDto.setFromId(-1L);
        userCenterDto.setToId(orderInfo.getBuyerId());
        userCenterDto.setDate(DateUtil.now());

        //从es中删除商品
        deleteFromES(orderInfo.getProductId());
        return true;
    }

    @ReleaseAnnotation(type = ReleaseType.GOODS, value = "#p0", operationType = OperationType.REMOVE)
    private void deleteFromES(Long productId){

    }

    @Override
    public List<OrderInfo> getAllDonationOrderInfo() {
        return orderInfoDao.selectAllDonationOrder();
    }

    @Override
    public Integer getOrderVersionById(Long id) {
        return orderInfoDao.selectOrderVersionById(id);
    }

    @Override
    public Message<String> createOrderCode(Long userId) {
        String code = String.valueOf(UuidUtils.nextId());
        redisTemplate.opsForValue().set(RedisUtil.getOrderCodeKey(userId), code, 5, TimeUnit.MINUTES);
        return Message.createBySuccess(code);
    }

    @Override
    public Message<OrderInfo> getOrderDetailById(Long id, Integer type, Long userId) {
        OrderInfo info = new OrderInfo();
        info.setId(id);

        //防止其他人恶意获得他人的订单信息
        if(type == 1) info.setSalerId(userId);
        else info.setBuyerId(userId);
        List<OrderInfo> orderInfoList = this.selectEntryList(info);
        if(orderInfoList == null || orderInfoList.isEmpty()) return Message.createBySuccess("未找到相关订单,刷新后重试");
        return Message.createBySuccess(orderInfoList.get(0));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userProductOrderList::", key = "#userId + '*'")
    })
    @Transactional
    public Message<String> cancelOrder(Long orderId, Long userId, Integer type) {
        List<OrderInfo> list = this.selectEntryList(orderId);
        if(list == null || list.isEmpty()) return Message.createByError("未找到相关订单");
        OrderInfo order = list.get(0);
        if(type == 1) {
            //买家删除
            if(!order.getBuyerId().equals(userId)) return Message.createByError("该订单不属于你,请刷新后重试");
            order.setDeleteState(1);
        }else if(type == 2){
            //卖家删除
            if(!order.getSalerId().equals(userId)) return Message.createByError("该订单不属于你,请刷新后重试");
            order.setDeleteState(2);
        }else {
            Long productId = order.getProductId();
            if(type == 3){
                //买家关闭订单
                String orderCodeKey = RedisUtil.getOrderCodeExpireKey(order.getOrderCode());
    //            Boolean hasKey = redisTemplate.hasKey(orderCodeKey);
    //            if(!hasKey) return Message.createByError("订单已经过期,请刷新后重试");
                if(order.getState() == 40){
                    //订单还没有支付,取消订单需要更新商品状态
                    if(!order.getBuyerId().equals(userId)) return Message.createByError("该订单不属于你,请刷新后重试");
                    boolean flag = goodsServiceFeignApi.updateProductState(productId, 2);
                    if(!flag){
                        log.error("用户关闭订单编号为{}的订单失败,未能改变编号为{}商品的状态", orderId, productId);
                        throw new OrderException("删除失败");
                    }
                    redisTemplate.delete(orderCodeKey);
                    order.setState(20);
                }
            }else{
                //订单超时
                boolean flag = goodsServiceFeignApi.updateProductState(productId, 2);
                if(!flag) throw new OrderException("删除失败,请稍后重试");
                order.setState(10);

                //发送消息
                SendUserCenterDto sendUserCenterDto = new SendUserCenterDto();
                sendUserCenterDto.setDate(DateUtil.now());
                sendUserCenterDto.setToId(order.getBuyerId());
                sendUserCenterDto.setNewsType(40);
                sendUserCenterDto.setFromId(-1L);
                sendUserCenterDto.setTargetId(orderId);
                sendUserCenterDto.setContent(new StringBuffer("您提交的编号为: ").append(order.getOrderCode()).append("的订单已经超时,系统已经自动取消该订单").toString());
                generateSendMessageService.sendToMq(sendUserCenterDto);
            }
        }
        this.updateByKey(order);
        return Message.createBySuccess();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "userProductOrderList::", key = "#orderInfo.buyerId + '*'")
    })
    public Integer completeTrade(OrderInfo orderInfo) {
        return orderInfoDao.completeTrade(orderInfo);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "userProductOrderList::", key = "#buyerId + '*'")
    })
    public Integer cancleOrderByCode(String orderCode, Long buyerId, Long orderId) {
        List<OrderInfo> list = this.selectEntryList(orderId);
        if(list == null || list.isEmpty()) return 0;
        OrderInfo order = list.get(0);
        order.setState(10);
        Integer temp = orderInfoDao.updateOrderStateByOrderCode(order);
        boolean flag = goodsServiceFeignApi.updateProductState(order.getProductId(), 2);
        if(!flag) return 0;
        if(temp == 1){
            SendUserCenterDto sendUserCenterDto = new SendUserCenterDto();
            sendUserCenterDto.setDate(DateUtil.now());
            sendUserCenterDto.setToId(buyerId);
            sendUserCenterDto.setNewsType(40);
            sendUserCenterDto.setFromId(-1L);
            sendUserCenterDto.setTargetId(orderId);
            sendUserCenterDto.setContent(new StringBuffer("您提交的编号为: ").append(orderCode).append("的订单已经超时,系统已经自动取消该订单").toString());
        }
        return temp;
    }

    @Override
    public Message<OrderInfo> getOrderInfoById(Long id, Long userId) {
        List<OrderInfo> orderInfoList = orderInfoDao.selectEntryList(id);
        if(orderInfoList == null || orderInfoList.isEmpty()) return Message.createByError("为找到相关信息");
        OrderInfo info = orderInfoList.get(0);
        if(userId.equals(info.getBuyerId()) || userId.equals(info.getSalerId())){
            return Message.createBySuccess(info);
        }
        log.error("---------->编号为{}的用户访问不属于他的订单{}", userId, id);
        return Message.createByError("非法访问他人数据,请立刻刷新页面重试");
    }

    @Override
    public Message<Page<OrderInfo>> getDonationOrderList(Page<OrderInfo> page) {
        int currentPage = page.getCurrentPage();
        int pageSize = page.getPageSize();
        OrderInfo searchCondition = page.getSearchCondition();
        if(searchCondition == null){
            searchCondition = new OrderInfo();
            searchCondition.setOrderField("payment_money");
            searchCondition.setOrderFieldType("DESC");
        }
        searchCondition.setStartIndex((currentPage - 1) * pageSize);
        searchCondition.setEndIndex(pageSize);
        Integer count = orderInfoDao.selectDonationCount();
        List<OrderInfo> orderInfoList = orderInfoDao.selectDonationListByCondition(searchCondition);
        page.setTotalCount(count);
        page.setData(orderInfoList);
        return Message.createBySuccess(page);
    }

    @Override
    public Integer updateOrderStateByIdAndVersion(Long id, Integer orderVersion, Integer state) {
        HashMap<String, String> param = new HashMap<>();
        param.put("id", id.toString());
        param.put("version", orderVersion.toString());
        param.put("state", state.toString());
        Integer i = orderInfoDao.updateOrderStateById(param);
        return i;
    }

    /**
     * @Author hmr
     * @Description 根据type和clazz获得结果
     * @Date: 2020/3/2 17:03
     * @param type
     * @param clazz 1-金额  2-订单数量
     * @reture: top.imuster.order.api.dto.OrderTrendDto
     **/
    private OrderTrendDto getResult(int type, int clazz){
        List<Long> increments = new ArrayList<>();
        List<Long> orderTotals = new ArrayList<>();
        List<String> abscissaUnit = new ArrayList<>();
        Long total = 0L;
        HashMap<String, String> param = new HashMap<>();
        List<String> start;
        List<String> end;
        Map<String, List<String>> times = new HashMap<>();
        if(type == 1){
            //最近七天
            times = TrendUtil.getCurrentWeekTime();
        }else if(type == 2){
            //最近一个月
            times = TrendUtil.getCurrentOneMonthTime();
        }else if(type == 3){
            times = TrendUtil.getSixMonthTime();
        }else if(type == 4){
            times = TrendUtil.getCurrentOneYearTime();
        }
        start = times.get("start");
        end = times.get("end");
        for (int i = 0; i < start.size(); i++) {
            if(i == 0){
                if(clazz == 1){
                    total = orderInfoDao.selectOrderAmountTotalByCreateTime(start.get(0));
                }else if(clazz == 2){
                    total = orderInfoDao.selectOrderTotalByCreateTime(start.get(0));
                }
                if(total == null) total = 0L;
            }
            param.put("start", start.get(i));
            param.put("end", end.get(i));
            Long increment = orderInfoDao.selectAmountIncrementTotal(param);
            if(increment == null) increment = 0L;  //* 重要,卡了一下午
            increments.add(increment);
            total += increment;
            orderTotals.add(total);
            abscissaUnit.add(end.get(i).substring(0, 10));
        }
        OrderTrendDto orderTrendDto = new OrderTrendDto();
        orderTrendDto.setAbscissaUnit(abscissaUnit);
        orderTrendDto.setIncrements(increments);
        orderTrendDto.setMax(total + 7);
        orderTrendDto.setOrderTotals(orderTotals);
        orderTrendDto.setInterval(total.intValue() / 15 + 1);
        return orderTrendDto;
    }

    /**
     * @Description: 校验商品是否还有库存，如果有，则锁定该商品不让其他人再次下单
     * @Author: hmr
     * @Date: 2019/12/28 10:50
     * @param productId
     * @reture:
     **/
    private ProductInfo checkProduct(Long productId, Integer productVersion){
        return goodsServiceFeignApi.lockStock(productId, productVersion);
    }
}