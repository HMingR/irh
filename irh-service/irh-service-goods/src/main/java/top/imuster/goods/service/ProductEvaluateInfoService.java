package top.imuster.goods.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductEvaluateInfo;
import top.imuster.order.api.pojo.OrderInfo;

/**
 * ProductEvaluateInfoService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface ProductEvaluateInfoService extends BaseService<ProductEvaluateInfo, Long> {

    /**
     * @Description: 根据订单编号给指定的商品进行评价
     * @Author: hmr
     * @Date: 2020/1/9 14:33
     * @param order
     * @param productEvaluateInfo
     * @reture: void
     **/
    Long evaluateByOrder(OrderInfo order, ProductEvaluateInfo productEvaluateInfo);

    /**
     * @Author hmr
     * @Description 发送消息
     * @Date: 2020/1/15 12:39
     * @reture: void
     *
     * @param orderId
     * @param productEvaluateInfo
     * @return*/
    Message<String> generateSendMessage(Long orderId, ProductEvaluateInfo productEvaluateInfo);
}