package top.imuster.order.provider.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.utils.TrendUtil;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.order.api.dto.OrderTrendDto;
import top.imuster.order.api.dto.ProductOrderDto;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.dao.OrderInfoDao;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.OrderInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public BaseDao<OrderInfo, Long> getDao() {
        return this.orderInfoDao;
    }

    @Override
    public OrderInfo getOrderInfoByOrderCode(String orderCode) {
        return orderInfoDao.selectOrderByOrderCode(orderCode);
    }

    @Override
    public OrderInfo getOrderByProduct(ProductOrderDto productOrderDto, Long userId){
        ProductInfo productInfo = productOrderDto.getProductInfo();
        OrderInfo orderInfo = productOrderDto.getOrderInfo();
        if(orderInfo.getOrderCode() == null){
            throw new OrderException("错误的订单信息,请刷新页面重新提交订单");
        }
        boolean b = checkProduct(productInfo.getId());
        if(!b){
            throw new OrderException("该商品已经不存在,请刷新后重试");
        }
        orderInfo.setProductId(productInfo.getId());
        orderInfo.setPaymentMoney(productInfo.getSalePrice());
        orderInfo.setSalerId(productInfo.getConsumerId());
        orderInfo.setBuyerId(userId);
        orderInfo.setState(40);
        orderInfo.setTradeType(orderInfo.getTradeType());
        Long id = orderInfoDao.insertOrder(orderInfo);
        if(id == null){
            log.error("插入订单返回插入值的id为null,订单信息为{}", orderInfo);
            throw new OrderException("生成订单失败,请稍后重试");
        }
        orderInfo.setId(id);
        return orderInfo;
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
    public Message<Page<OrderInfo>> list(Integer pageSize, Integer currentPage, Long userId, Integer type) {
        Page<OrderInfo> page = new Page<>();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setStartIndex((currentPage < 1 ? 1 : currentPage - 1) * pageSize);
        if(type == 1){
            //买家
            orderInfo.setState(30);
            orderInfo.setBuyerId(userId);
        }else{
            //卖家
            orderInfo.setState(35);
            orderInfo.setSalerId(userId);
        }
        Integer count = orderInfoDao.selectOrderListCountByUserId(orderInfo);
        List<OrderInfo> list = orderInfoDao.selectOrderListByUserId(orderInfo);
        page.setTotalCount(count);
        page.setData(list);
        return Message.createBySuccess(page);
    }

    @Override
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
        orderInfoDao.updateByKey(orderInfo);
        return Message.createBySuccess();
    }

    @Override
    public List<OrderInfo> getAllDonationOrderInfo() {
        return orderInfoDao.selectAllDonationOrder();
    }

    @Override
    public Integer getOrderVersionById(Long id) {
        return orderInfoDao.selectOrderVersionById(id);
    }

    /**
     * @Author hmr
     * @Description 根据type和clazz获得结果
     * @Date: 2020/3/2 17:03
     * @param type
     * @param clazz 1-金额  2-订单数量
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
    private boolean checkProduct(Long productId){
        return goodsServiceFeignApi.lockStock(productId);
    }
}