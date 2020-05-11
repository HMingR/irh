package top.imuster.order.provider.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendUserCenterDto;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.pojo.ProductEvaluateInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;
import top.imuster.order.provider.dao.ProductEvaluateInfoDao;
import top.imuster.order.provider.service.ProductEvaluateInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ProductEvaluateInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productEvaluateInfoService")
public class ProductEvaluateInfoServiceImpl extends BaseServiceImpl<ProductEvaluateInfo, Long> implements ProductEvaluateInfoService {

    @Resource
    private ProductEvaluateInfoDao productEvaluateInfoDao;

    @Autowired
    GoodsServiceFeignApi goodsServiceFeignApi;

    @Resource
    private GenerateSendMessageService generateSendMessageService;

    @Autowired
    OrderServiceFeignApi orderServiceFeignApi;

    @Override
    public BaseDao<ProductEvaluateInfo, Long> getDao() {
        return this.productEvaluateInfoDao;
    }

    @Override
    public Long evaluateByOrder(OrderInfo order, ProductEvaluateInfo productEvaluateInfo){
        ProductEvaluateInfo evaluateInfo = new ProductEvaluateInfo();
        evaluateInfo.setProductId(order.getProductId());
        evaluateInfo.setSalerId(order.getSalerId());
        evaluateInfo.setOrderId(order.getId());
        evaluateInfo.setState(2);
        productEvaluateInfoDao.insertEntry(evaluateInfo);
        return evaluateInfo.getId();
    }

    @Override
    public Message<String> writeEvaluateByOrderId(Long orderId, ProductEvaluateInfo productEvaluateInfo) {
        OrderInfo order = orderServiceFeignApi.getOrderById(orderId);
        if(order == null){
            return Message.createByError("未找到相应的订单,请刷新后重试");
        }if(order.getState() != 50){
            return Message.createByError("该订单还没有完成交易，完成该订单之后才能进行评价");
        }if(!order.getBuyerId().equals(productEvaluateInfo.getBuyerId())){
            return Message.createByError("参数错误,您不是该订单的买家,请刷新后重试");
        }
        Long id = this.evaluateByOrder(order, productEvaluateInfo);
        SendUserCenterDto sendMessageDto = new SendUserCenterDto();
        sendMessageDto.setToId(order.getSalerId());
        sendMessageDto.setFromId(order.getBuyerId());
        sendMessageDto.setResourceId(id);
        sendMessageDto.setTargetId(productEvaluateInfo.getProductId());
        sendMessageDto.setNewsType(60);
        sendMessageDto.setContent(productEvaluateInfo.getContent());
        generateSendMessageService.sendToMq(sendMessageDto);
        return Message.createBySuccess();
    }

    @Override
    public Message<Page<ProductEvaluateInfo>> getListByUserId(Integer pageSize, Integer currentPage, Long userId, Integer type) {
        Page<ProductEvaluateInfo> page = new Page<>();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        ProductEvaluateInfo productEvaluateInfo = new ProductEvaluateInfo();
        if(type == 1) productEvaluateInfo.setBuyerId(userId);
        if(type == 2) productEvaluateInfo.setSalerId(userId);
        productEvaluateInfo.setState(2);
        page = this.selectPage(productEvaluateInfo, page);
        List<ProductEvaluateInfo> data = page.getData();
        if(data != null && !data.isEmpty()){
            data.stream().forEach(condition -> {
                Long productId = condition.getProductId();
                ProductInfo briefInfoById = goodsServiceFeignApi.getProductBriefInfoById(productId);
                condition.setProductBrief(briefInfoById);
            });
        }
        return Message.createBySuccess(page);
    }

    @Override
    public Long getEvaluateIdByOrderId(Long id) {
        return productEvaluateInfoDao.selectIdByOrderId(id);
    }
}