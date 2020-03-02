package top.imuster.order.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.order.api.pojo.OrderInfo;

import java.util.HashMap;

/**
 * OrderInfoDao 接口
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public interface OrderInfoDao extends BaseDao<OrderInfo, Long> {
    //自定义扩展

    /**
     * @Description: 通过订单号查询订单信息
     * @Author: hmr
     * @Date: 2019/12/23 21:30
     * @param orderCode
     * @reture: top.imuster.order.api.pojo.OrderInfo
     **/
    OrderInfo selectOrderByOrderCode(String orderCode);

    /**
     * @Author hmr
     * @Description 存储订单信息,并且返回生成的id
     * @Date: 2020/2/6 12:04
     * @param orderInfo
     * @reture: java.lang.Long
     **/
    Long insertOrder(OrderInfo orderInfo);

    /**
     * @Author hmr
     * @Description 根据开始日期和结束日期获得该区域内的订单金额增长量
     * @Date: 2020/3/2 16:29
     * @param param
     * @reture: java.lang.Long
     **/
    Long selectAmountIncrementTotal(HashMap<String, String> param);

    /**
     * @Author hmr
     * @Description 根据开始时间获得该时间之前的所有有效的订单总金额
     * @Date: 2020/3/2 16:35
     * @param s 开始时间
     * @reture: long
     *
     * @return*/
    Long selectOrderAmountTotalByCreateTime(String s);

    /**
     * @Author hmr
     * @Description 根据时间获得该时间前生成的订单数量
     * @Date: 2020/3/2 17:00
     * @param s
     * @reture: long
     **/
    long selectOrderTotalByCreateTime(String s);
}