package top.imuster.order.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.order.api.pojo.ErrandOrderInfo;

/**
 * ErrandOrderDao 接口
 * @author 黄明人
 * @since 2020-02-11 17:49:36
 */
public interface ErrandOrderDao extends BaseDao<ErrandOrderInfo, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 根据订单code查看订单状态
     * @Date: 2020/2/12 11:51
     * @param code
     * @reture: java.lang.Integer
     **/
    Integer selectOrderStateByCode(String code);

    /**
     * @Author hmr
     * @Description 插入信息并且返回id
     * @Date: 2020/4/29 11:17
     * @param order
     * @reture: java.lang.Long
     **/
    Long insertInfoReturnId(ErrandOrderInfo order);
}