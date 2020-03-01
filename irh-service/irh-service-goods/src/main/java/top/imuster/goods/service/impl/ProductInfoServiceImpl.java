package top.imuster.goods.service.impl;




import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.common.core.enums.MqTypeEnum;
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


    @Override
    public BaseDao<ProductInfo, Long> getDao() {
        return this.productInfoDao;
    }

    @Override
    public Integer updateProductCategoryByCondition(ProductInfo productInfo) {
        return productInfoDao.updateProductCategoryByCondition(productInfo);
    }

    @Override
    public void generateDetailPage(SendMessageDto sendMessageDto) {
        sendMessageDto.setType(MqTypeEnum.DETAIL);
    }

    @Override
    public String getConsumerEmailById(Long id) {
        return productInfoDao.selectSalerEmailByProductId(id);
    }

    @Override
    public ProductInfo getProductInfoByMessageId(Long id) {
        return productInfoDao.selectProductInfoByMessageId(id);
    }

    @Override
    public String getMainPicUrlById(Long id) {
        return productInfoDao.selectMainPicUrlById(id);
    }
}