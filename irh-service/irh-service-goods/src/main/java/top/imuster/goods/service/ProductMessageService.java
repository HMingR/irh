package top.imuster.goods.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductMessageInfo;

/**
 * ProductMessageService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface ProductMessageService extends BaseService<ProductMessageInfo, Long> {

    /**
     * @Author hmr
     * @Description 发送消息
     * @Date: 2020/1/15 13:44
     * @param sendMessageDto
     * @param parentId 如果parentId是0，则给卖家发送消息；如果不是，则给卖家和parentId对应的会员发送消息
     * @reture: void
     **/
    void generateSendMessage(ProductMessageInfo productMessageInfo) throws Exception;


    Message<Page<ProductMessageInfo>> getMessagePage(Integer pageSize, Integer currentPage, Long goodsId, Long parentId, Long firstClassId);
}