package top.imuster.goods.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendDetailPageDto;
import top.imuster.common.core.enums.TemplateEnum;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.ProductInfoDao;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;

/**
 * ProductInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productInfoService")
public class ProductInfoServiceImpl extends BaseServiceImpl<ProductInfo, Long> implements ProductInfoService {

    @Resource
    private ProductInfoDao productInfoDao;

    @Resource
    private GenerateSendMessageService generateSendMessageService;


    @Override
    public BaseDao<ProductInfo, Long> getDao() {
        return this.productInfoDao;
    }

    @Override
    public Integer updateProductCategoryByCondition(ProductInfo productInfo) {
        return productInfoDao.updateProductCategoryByCondition(productInfo);
    }

    @Override
    public Long getConsumerIdById(Long id) {
        return productInfoDao.selectSalerIdByProductId(id);
    }

    @Override
    public ProductInfo getProductInfoByMessageId(Long id) {
        return productInfoDao.selectProductInfoByMessageId(id);
    }

    @Override
    public String getDetailPageUrlById(Long id) {
        return productInfoDao.selectDetailPageUrlById(id);
    }

    @Override
    public Message<String> releaseProduct(ProductInfo productInfo) {
        Long id = productInfoDao.insertInfoReturnId(productInfo);
        SendDetailPageDto sendMessage = new SendDetailPageDto();
        productInfo.setId(id);
        sendMessage.setObject(productInfo);
        sendMessage.setTemplateEnum(TemplateEnum.PRODUCT_TEMPLATE);
        generateSendMessageService.sendToMq(sendMessage);
        return Message.createBySuccess();
    }

    @Override
    public Message<Page<ProductInfo>> list(Long userId, Integer pageSize, Integer currentPage) {
        Page<ProductInfo> infoPage = new Page<>();
        ProductInfo productInfo = new ProductInfo();
        productInfo.setConsumerId(userId);
        productInfo.setState(2);
        infoPage = this.selectPage(productInfo, infoPage);
        return Message.createBySuccess(infoPage);
    }
}