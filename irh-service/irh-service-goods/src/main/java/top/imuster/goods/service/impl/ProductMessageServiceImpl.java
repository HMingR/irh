package top.imuster.goods.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.dto.SendUserCenterDto;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.goods.api.pojo.ProductMessage;
import top.imuster.goods.dao.ProductMessageDao;
import top.imuster.goods.service.ProductInfoService;
import top.imuster.goods.service.ProductMessageService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductMessageService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productMessageService")
public class ProductMessageServiceImpl extends BaseServiceImpl<ProductMessage, Long> implements ProductMessageService {

    @Resource
    private ProductMessageDao productMessageDao;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Resource
    ProductInfoService productInfoService;

    @Override
    public BaseDao<ProductMessage, Long> getDao() {
        return this.productMessageDao;
    }

    @Override
    public List<ProductMessage> generateMessageTree(Long id) {
        ProductMessage condition = new ProductMessage();
        condition.setState(2);
        condition.setProductId(id);
        List<ProductMessage> allMessage = productMessageDao.selectEntryList(condition);
        List<ProductMessage> tree = generateTree(allMessage);
        return tree;
    }

    @Override
    public void generateSendMessage(ProductMessage productMessage){
        Long writer = productMessage.getConsumerId();  //写留言的人
        Long messageId = productMessageDao.insertReturnId(productMessage);  //插入留言后返回的id
        SendUserCenterDto sendToSaler = new SendUserCenterDto();
        sendToSaler.setType(MqTypeEnum.CENTER);
        if(productMessage.getParentId() == 0){
            Long salerId = productInfoService.getConsumerIdById(productMessage.getProductId());
            sendToSaler.setFromId(writer);
            sendToSaler.setToId(salerId);
            if(salerId.equals(writer)) return;   //卖家评论自己
            sendToSaler.setContent("有人对你的商品进行了留言:" + productMessage.getContent());
            sendToSaler.setResourceId(messageId);
        } else {
            long toId = productMessageDao.selectSalerIdByMessageParentId(productMessage.getParentId());
            sendToSaler.setContent("有人对你的留言进行了回复:" + productMessage.getContent());
            if(writer.equals(toId)) return;  //楼主评论自己
            sendToSaler.setToId(toId);
            sendToSaler.setFromId(writer);
            sendToSaler.setResourceId(messageId);
        }
        generateSendMessageService.sendToMq(sendToSaler);
    }

    private List<ProductMessage> generateTree(List<ProductMessage> list){
        List<ProductMessage> result = new ArrayList<>();
        // 1、获取第一级节点
        for (ProductMessage productMessage : list) {
            if(0 == productMessage.getParentId()) {
                result.add(productMessage);
            }
        }
        // 2、递归获取子节点
        for (ProductMessage parent : result) {
            parent = recursiveTree(parent, list);
        }
        return result;
    }

    private ProductMessage recursiveTree(ProductMessage parent, List<ProductMessage> list) {
        for (ProductMessage productMessage : list) {
            if(parent.getId() == productMessage.getParentId()) {
                productMessage = recursiveTree(productMessage, list);
                parent.getChilds().add(productMessage);
            }
        }
        return parent;
    }

}