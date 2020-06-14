package top.imuster.goods.service.impl;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.ReleaseAnnotation;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.dto.rabbitMq.SendExamineDto;
import top.imuster.common.core.enums.OperationType;
import top.imuster.common.core.enums.ReleaseType;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.goods.api.dto.GoodsForwardDto;
import top.imuster.goods.api.dto.UserGoodsCenterDto;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.ProductInfoDao;
import top.imuster.goods.exception.GoodsException;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    public ProductInfo getProductBriefInfoById(Long id) {
        ProductInfo productInfo = productInfoDao.selectProductBriefInfoById(id);
        return productInfo;
    }

    @Override
    @CacheEvict(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "#productInfo.consumerId + '::userEsProductList::*'")
    public Message<String> releaseProduct(ProductInfo productInfo) {
        productInfoDao.insertEntry(productInfo);

        SendExamineDto sendExamineDto = new SendExamineDto();
        sendExamineDto.setTargetId(productInfo.getId());
        sendExamineDto.setTargetType(1);
        sendExamineDto.setUserId(productInfo.getConsumerId());
        sendExamineDto.setContent(new StringBuffer(productInfo.getTitle()).append(productInfo.getProductDesc()).toString());

        ArrayList<String> strings = new ArrayList<>();
        strings.add(productInfo.getMainPicUrl());

        if(productInfo.getOtherImgUrl() != null){
            String otherImgUrl = productInfo.getOtherImgUrl();
            String[] split = otherImgUrl.split("," );
            List<String> imgs = Arrays.asList(split);
            strings.addAll(imgs);
        }
        sendExamineDto.setImgUrl(strings);
        generateSendMessageService.sendToMq(sendExamineDto);
        return Message.createBySuccess();
    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "#userId + '::userEsProductList::' + #currentPage + '::type::' + #type")
    public Message<Page<ProductInfo>> list(Long userId, Integer pageSize, Integer currentPage, int type) {
        Page<ProductInfo> infoPage = new Page<>();
        infoPage.setPageSize(pageSize);
        infoPage.setCurrentPage(currentPage);
        ProductInfo productInfo = new ProductInfo();
        productInfo.setConsumerId(userId);
//        productInfo.setState(2);
        if(type == 2) productInfo.setState(2);
        productInfo.setOrderField("create_time");
        productInfo.setOrderFieldType("DESC");
        productInfo.setStartIndex((currentPage - 1) * pageSize);
        productInfo.setEndIndex(pageSize);
        List<ProductInfo> productInfos = productInfoDao.selectProductBriefInfoList(productInfo);
        Integer count = productInfoDao.selectEntryListCount(productInfo);
        infoPage.setData(productInfos);
        infoPage.setTotalCount(count);
        return Message.createBySuccess(infoPage);
    }

    @Override
    public void transBrowserTimesFromRedis2DB(List<BrowserTimesDto> browserTimesDtos) {
        if(CollectionUtils.isEmpty(browserTimesDtos)) return;
        Integer total = productInfoDao.updateBrowseTimesByDtoList(browserTimesDtos);
        log.info("------------>一共更新了{}条浏览总数记录", total);
    }

    @Override
    @CacheEvict(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "#userId + '::userEsProductList::*'")
    @ReleaseAnnotation(type = ReleaseType.GOODS, value = "#p0", operationType = OperationType.REMOVE)
    public Message<String> deleteById(Long id, Long userId) {
        Long userIdByProductId = productInfoDao.selectUserIdByProductId(id);
        if(userIdByProductId == null) return Message.createByError("删除失败,请刷新后重试");
        if(!userId.equals(userIdByProductId)){
            log.error("id为{}的用户试图删除id为{}的商品，但是该商品不属于他", userId, id);
            throw new GoodsException("非法操作,你当前的操作已经被记录");
        }
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(id);
        productInfo.setState(1);
        productInfoDao.updateByKey(productInfo);
        return Message.createBySuccess();
    }

    @Override
    public void updateProductCollectTotal(List<GoodsForwardDto> list) {
        productInfoDao.updateCollectTotal(list);
    }

    @Override
    public Integer lockProduct(Long productId, Integer version) {
        HashMap<String, String> params = new HashMap<>();
        params.put("productId", String.valueOf(productId));
        params.put("version", String.valueOf(version));
        return productInfoDao.lockProductById(params);
    }

    @Override
    public Message<Page<ProductInfo>> getProductBriefInfoByPage(Integer currentPage, Integer pageSize) {
        Page<ProductInfo> page = new Page<>();
        ProductInfo condition = new ProductInfo();
        condition.setState(2);
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        condition.setStartIndex((currentPage - 1) * pageSize);
        condition.setEndIndex(pageSize);
        List<ProductInfo> list = productInfoDao.selectProductBriefInfoList(condition);
        Integer count = productInfoDao.selectEntryListCount(condition);
        page.setTotalCount(count);
        page.setData(list);
        return Message.createBySuccess(page);
    }

    @Override
    @CacheEvict(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "'*userEsProductList'")
    public boolean updateProductStateById(Long productId, Integer state) {
        ProductInfo condition = new ProductInfo();
        condition.setId(productId);
        condition.setState(state);
        Integer temp = productInfoDao.updateProductStateById(condition);
        return temp != 0;
    }

    @Override
    public List<ProductInfo> getProductBriefByIds(List<Long> ids) {
        return productInfoDao.selectProductBriefInfoByIds(ids);
    }

    @Override
    public Message<UserGoodsCenterDto> getUserCenterInfoById(Long id) {
        UserGoodsCenterDto userGoodsCenterDto = productInfoDao.selectGoodsBrowseTotalByUserId(id);
        String donationTotal = productInfoDao.selectDonationMoneyByUserId(id);
        String totalMoney = donationTotal == null ? "0" : donationTotal;
        Integer saleTotal = productInfoDao.selectSaleTotalByUserId(id);
        userGoodsCenterDto.setDonationTotal(totalMoney);
        userGoodsCenterDto.setSaleTotal(saleTotal);
        return Message.createBySuccess(userGoodsCenterDto);
    }
}