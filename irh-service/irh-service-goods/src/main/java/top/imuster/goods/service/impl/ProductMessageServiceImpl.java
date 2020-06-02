package top.imuster.goods.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.goods.api.pojo.ProductMessageInfo;
import top.imuster.goods.dao.ProductMessageDao;
import top.imuster.goods.service.ProductInfoService;
import top.imuster.goods.service.ProductMessageService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Long productId = productMessageInfo.getProductId();
        Long parentId = productMessageInfo.getParentId();
        productMessageDao.insertEntry(productMessageInfo);  //插入留言后返回的id
        Long messageId = productMessageInfo.getId();
        SendUserCenterDto sendToDto = new SendUserCenterDto();
        if(parentId == -1){
            Long salerId = productInfoService.getConsumerIdById(productId);
            if(salerId.equals(writer)) return;   //卖家评论自己,不发送消息
            sendToDto.setFromId(writer);
            sendToDto.setToId(salerId);
            sendToDto.setContent(productMessageInfo.getContent());
            sendToDto.setNewsType(10);
            sendToDto.setTargetId(productId);
//            sendToDto.setResourceId(messageId);
            sendToDto.setResourceId(productId);
        } else {
            Long toId = productMessageDao.selectUserIdByMessageParentId(parentId);
            if(toId == null || writer.equals(toId)) return;  //楼主评论自己，不发送消息
            sendToDto.setContent(productMessageInfo.getContent());
            sendToDto.setToId(toId);
            sendToDto.setFromId(writer);
            sendToDto.setNewsType(10);
            sendToDto.setTargetId(productId);
            sendToDto.setResourceId(parentId);
        }
        generateSendMessageService.sendToMq(sendToDto);
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
            Map<Long, Long> collect = data.stream().collect(Collectors.toMap(ProductMessageInfo::getId, ProductMessageInfo::getConsumerId));
            data.stream().forEach(productMessageInfo -> {
                Long id = productMessageInfo.getId();
                Long pid = productMessageInfo.getParentId();
                Integer replyTotal = productMessageDao.selectReplyTotalById(id);
                productMessageInfo.setReplyTotal(replyTotal);
                Long parentWriterId = collect.get(pid);

                //当查询到的该页面中没有某一条留言的父留言，通过这个查询夫留言的作者，但是必须是三级留言
                if(parentWriterId == null && productMessageInfo.getParentId() != -1){
                    parentWriterId = productMessageDao.selectParentWriterIdById(pid);
                }
                productMessageInfo.setParentWriterId(parentWriterId);
            });
        }
        return Message.createBySuccess(page);
    }

}