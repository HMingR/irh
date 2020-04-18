package top.imuster.order.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.pojo.ProductDonationOrderRel;

import java.util.List;

/**
 * ProductDonationOrderRelService接口
 * @author 黄明人
 * @since 2020-04-14 16:45:13
 */
public interface ProductDonationOrderRelService extends BaseService<ProductDonationOrderRel, Long> {

    /**
     * @Author hmr
     * @Description 根据applyId查看使用了哪些订单
     * @Date: 2020/4/18 17:51
     * @param applyId
     * @reture: java.util.List<top.imuster.order.api.pojo.OrderInfo>
     **/
    List<OrderInfo> getOrderInfoByApplyId(Long applyId);
}