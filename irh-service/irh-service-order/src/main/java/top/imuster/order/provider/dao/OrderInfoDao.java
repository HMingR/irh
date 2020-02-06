package top.imuster.order.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.order.api.pojo.OrderInfo;

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

}