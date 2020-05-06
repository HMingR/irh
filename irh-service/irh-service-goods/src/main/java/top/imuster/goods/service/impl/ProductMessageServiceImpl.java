package top.imuster.goods.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendUserCenterDto;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.goods.api.pojo.ProductMessageInfo;
import top.imuster.goods.dao.ProductMessageDao;
import top.imuster.goods.service.ProductInfoService;
import top.imuster.goods.service.ProductMessageService;

import javax.annotation.Resource;
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
    public void generateSendMessage(ProductMessageInfo productMessageInfo){
        Long writer = productMessageInfo.getConsumerId();  //写留言的人
        Long messageId = productMessageDao.insertReturnId(productMessageInfo);  //插入留言后返回的id
        SendUserCenterDto sendToSaler = new SendUserCenterDto();
        if(productMessageInfo.getParentId() == 0){
            Long salerId = productInfoService.getConsumerIdById(productMessageInfo.getProductId());
            sendToSaler.setFromId(writer);
            sendToSaler.setToId(salerId);
            if(salerId.equals(writer)) return;   //卖家评论自己,不发送消息
            sendToSaler.setContent("有人对你的商品进行了留言");
            sendToSaler.setNewsType(10);
            sendToSaler.setResourceId(messageId);
        } else {
            long toId = productMessageDao.selectSalerIdByMessageParentId(productMessageInfo.getParentId());
            if(writer.equals(toId)) return;  //楼主评论自己，不发送消息
            sendToSaler.setContent("有人对你的留言进行了回复");
            sendToSaler.setToId(toId);
            sendToSaler.setFromId(writer);
            sendToSaler.setNewsType(10);
            sendToSaler.setResourceId(messageId);
        }
        generateSendMessageService.sendToMq(sendToSaler);
    }

    @Override
    public Message<Page<ProductMessageInfo>> getMessagePage(Integer pageSize, Integer currentPage, Long goodsId, Long parentId, Long firstClassId) {
        Page<ProductMessageInfo> page = new Page<>();
        ProductMessageInfo condition = new ProductMessageInfo();
        page.setPageSize(pageSize < 1 ? 10 : pageSize);
        page.setCurrentPage(currentPage < 1? 1: currentPage);
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        condition.setProductId(goodsId);
        condition.setFirstClassId(firstClassId);
        condition.setParentId(parentId);
        page = this.selectPage(condition, page);
        List<ProductMessageInfo> data = page.getData();
        if(data != null && !data.isEmpty()){
            data.stream().forEach(productMessageInfo -> {
                Long id = productMessageInfo.getId();
                Integer replyTotal = productMessageDao.selectReplyTotalById(id);
                productMessageInfo.setReplyTotal(replyTotal);
            });
        }
        return Message.createBySuccess(page);
    }

}