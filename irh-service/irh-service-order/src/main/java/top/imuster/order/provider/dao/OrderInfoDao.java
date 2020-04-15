package top.imuster.order.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.order.api.pojo.OrderInfo;

import java.util.HashMap;
import java.util.List;

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

    /**
     * @Author hmr
     * @Description 根据订单code获得订单的版本信息
     * @Date: 2020/3/20 10:43
     * @param orderCode
     * @reture: java.lang.Integer
     **/
    Integer selectOrderVersionByCode(String orderCode);

    /**
     * @Author hmr
     * @Description 用户查看订单的时候计算属于他的订单的数量
     * @Date: 2020/4/13 14:52
     * @param orderInfo
     * @reture: java.lang.Integer
     **/
    Integer selectOrderListCountByUserId(OrderInfo orderInfo);

    /**
     * @Author hmr
     * @Description 用户查看订单的时候计算属于他的订单
     * @Date: 2020/4/13 15:01
     * @param orderInfo
     * @reture: java.util.List<top.imuster.order.api.pojo.OrderInfo>
     **/
    List<OrderInfo> selectOrderListByUserId(OrderInfo orderInfo);

    /**
     * @Author hmr
     * @Description 获得所有选择捐赠的订单的订单id、订单金额、订单版本
     * @Date: 2020/4/15 9:34
     * @param
     * @reture: java.util.List<top.imuster.order.api.pojo.OrderInfo>
     **/
    List<OrderInfo> selectAllDonationOrder();

    /**
     * @Author hmr
     * @Description 根据订单id获得订单的版本信息
     * @Date: 2020/4/15 16:16
     * @param id
     * @reture: java.lang.Integer
     **/
    Integer selectOrderVersionById(Long id);
}