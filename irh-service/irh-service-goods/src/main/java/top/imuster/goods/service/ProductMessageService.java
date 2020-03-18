package top.imuster.goods.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.goods.api.pojo.ProductMessageInfo;

import java.util.List;

/**
 * ProductMessageService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface ProductMessageService extends BaseService<ProductMessageInfo, Long> {

    /**
     * @Description: 根据id生成某个商品的留言树
     * @Author: hmr
     * @Date: 2020/1/9 15:23
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    List<ProductMessageInfo> generateMessageTree(Long id);

    /**
     * @Author hmr
     * @Description 发送消息
     * @Date: 2020/1/15 13:44
     * @param sendMessageDto
     * @param parentId 如果parentId是0，则给卖家发送消息；如果不是，则给卖家和parentId对应的会员发送消息
     * @reture: void
     **/
    void generateSendMessage(ProductMessageInfo productMessageInfo) throws Exception;


}