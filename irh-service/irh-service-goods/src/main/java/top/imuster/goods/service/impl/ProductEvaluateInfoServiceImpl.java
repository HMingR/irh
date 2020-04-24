package top.imuster.goods.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendUserCenterDto;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.goods.api.pojo.ProductEvaluateInfo;
import top.imuster.goods.dao.ProductEvaluateInfoDao;
import top.imuster.goods.service.ProductEvaluateInfoService;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;

import javax.annotation.Resource;

/**
 * ProductEvaluateInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productEvaluateInfoService")
public class ProductEvaluateInfoServiceImpl extends BaseServiceImpl<ProductEvaluateInfo, Long> implements ProductEvaluateInfoService {

    @Resource
    private ProductEvaluateInfoDao productEvaluateInfoDao;

    @Resource
    private GenerateSendMessageService generateSendMessageService;

    @Autowired
    OrderServiceFeignApi orderServiceFeignApi;

    @Override
    public BaseDao<ProductEvaluateInfo, Long> getDao() {
        return this.productEvaluateInfoDao;
    }

    @Override
    public void evaluateByOrder(OrderInfo order, ProductEvaluateInfo productEvaluateInfo){
        ProductEvaluateInfo evaluateInfo = new ProductEvaluateInfo();
        evaluateInfo.setBuyerId(order.getBuyerId());
        evaluateInfo.setProductId(order.getProductId());
        evaluateInfo.setSalerId(order.getSalerId());
        evaluateInfo.setOrderId(order.getId());
        evaluateInfo.setState(2);
        productEvaluateInfoDao.insertEntry(evaluateInfo);
    }

    @Override
    public Message<String> generateSendMessage(Long orderId, ProductEvaluateInfo productEvaluateInfo) {
        OrderInfo order = orderServiceFeignApi.getOrderById(orderId);
        if(order == null){
            return Message.createByError("未找到相应的订单,请刷新后重试");
        }
        if(order.getState() != 50){
            return Message.createByError("该订单还没有完成交易，完成该订单之后才能进行评价");
        }
        this.evaluateByOrder(order, productEvaluateInfo);
        SendUserCenterDto sendMessageDto = new SendUserCenterDto();
        sendMessageDto.setToId(order.getSalerId());
        sendMessageDto.setFromId(order.getBuyerId());
        sendMessageDto.setContent("我对您发布的商品进行了评价,快来看看吧");
        generateSendMessageService.sendToMq(sendMessageDto);
        return Message.createBySuccess();
    }
}