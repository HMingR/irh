package top.imuster.order.provider.dao;

import top.imuster.common.base.dao.BaseDao;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.pojo.ProductDonationOrderRel;

import java.util.List;

/**
 * ProductDonationOrderRelDao 接口
 * @author 黄明人
 * @since 2020-04-14 16:45:13
 */
public interface ProductDonationOrderRelDao extends BaseDao<ProductDonationOrderRel, Long> {
    //自定义扩展

    /**
     * @Author hmr
     * @Description 通过已经发放资金的申请id查看使用了哪些订单
     * @Date: 2020/4/16 9:18
     * @param applyId
     * @reture: java.util.List<top.imuster.order.api.pojo.OrderInfo>
     **/
    List<OrderInfo> selectOrderListByFinishApplyId(Long applyId);
}