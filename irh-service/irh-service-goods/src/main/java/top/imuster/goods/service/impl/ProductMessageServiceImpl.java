package top.imuster.goods.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.dto.SendUserCenterDto;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.goods.api.pojo.ProductMessageInfo;
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
public class ProductMessageServiceImpl extends BaseServiceImpl<ProductMessageInfo, Long> implements ProductMessageService {

    @Resource
    private ProductMessageDao productMessageDao;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Resource
    ProductInfoService productInfoService;

    @Override
    public BaseDao<ProductMessageInfo, Long> getDao() {
        return this.productMessageDao;
    }

    @Override
    public List<ProductMessageInfo> generateMessageTree(Long id) {
        ProductMessageInfo condition = new ProductMessageInfo();
        condition.setState(2);
        condition.setProductId(id);
        List<ProductMessageInfo> allMessage = productMessageDao.selectEntryList(condition);
        List<ProductMessageInfo> tree = generateTree(allMessage);
        return tree;
    }

    @Override
    public void generateSendMessage(ProductMessageInfo productMessageInfo){
        Long writer = productMessageInfo.getConsumerId();  //写留言的人
        Long messageId = productMessageDao.insertReturnId(productMessageInfo);  //插入留言后返回的id
        SendUserCenterDto sendToSaler = new SendUserCenterDto();
        sendToSaler.setType(MqTypeEnum.CENTER);
        if(productMessageInfo.getParentId() == 0){
            Long salerId = productInfoService.getConsumerIdById(productMessageInfo.getProductId());
            sendToSaler.setFromId(writer);
            sendToSaler.setToId(salerId);
            if(salerId.equals(writer)) return;   //卖家评论自己
            sendToSaler.setContent("有人对你的商品进行了留言:" + productMessageInfo.getContent());
            sendToSaler.setResourceId(messageId);
        } else {
            long toId = productMessageDao.selectSalerIdByMessageParentId(productMessageInfo.getParentId());
            sendToSaler.setContent("有人对你的留言进行了回复:" + productMessageInfo.getContent());
            if(writer.equals(toId)) return;  //楼主评论自己
            sendToSaler.setToId(toId);
            sendToSaler.setFromId(writer);
            sendToSaler.setResourceId(messageId);
        }
        generateSendMessageService.sendToMq(sendToSaler);
    }

    private List<ProductMessageInfo> generateTree(List<ProductMessageInfo> list){
        List<ProductMessageInfo> result = new ArrayList<>();
        // 1、获取第一级节点
        for (ProductMessageInfo productMessageInfo : list) {
            if(0 == productMessageInfo.getParentId()) {
                result.add(productMessageInfo);
            }
        }
        // 2、递归获取子节点
        for (ProductMessageInfo parent : result) {
            parent = recursiveTree(parent, list);
        }
        return result;
    }

    private ProductMessageInfo recursiveTree(ProductMessageInfo parent, List<ProductMessageInfo> list) {
        for (ProductMessageInfo productMessageInfo : list) {
            if(parent.getId() == productMessageInfo.getParentId()) {
                productMessageInfo = recursiveTree(productMessageInfo, list);
                parent.getChilds().add(productMessageInfo);
            }
        }
        return parent;
    }

}