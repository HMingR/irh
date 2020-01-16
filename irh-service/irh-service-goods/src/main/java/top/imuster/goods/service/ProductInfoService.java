package top.imuster.goods.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.goods.api.pojo.ProductInfo;

/**
 * ProductInfoService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface ProductInfoService extends BaseService<ProductInfo, Long> {

    /**
     * @Description: 按条件更新指定商品的类别
     * @Author: hmr
     * @Date: 2019/12/22 15:26
     * @param productInfo
     * @reture: void
     **/
    Integer updateProductCategoryByCondition(ProductInfo productInfo);

    /**
     * @Author hmr
     * @Description 向消息队列中发送消息，生成商品详情页
     * @Date: 2020/1/16 10:40
     * @param sendMessageDto
     * @reture: void
     **/
    void generateDetailPage(SendMessageDto sendMessageDto);

}