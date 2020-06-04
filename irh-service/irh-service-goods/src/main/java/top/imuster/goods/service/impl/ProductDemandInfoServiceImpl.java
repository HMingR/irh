package top.imuster.goods.service.impl;


import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.ReleaseAnnotation;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.enums.OperationType;
import top.imuster.common.core.enums.ReleaseType;
import top.imuster.goods.api.dto.ESProductDto;
import top.imuster.goods.api.dto.GoodsForwardDto;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.dao.ProductDemandInfoDao;
import top.imuster.goods.exception.GoodsException;
import top.imuster.goods.service.ProductDemandInfoService;
import top.imuster.goods.service.ProductDemandReplyInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ProductDemandInfoService 实现类
 * @author 黄明人
 * @since 2020-01-16 10:19:41
 */
@Service("productDemandInfoService")
public class ProductDemandInfoServiceImpl extends BaseServiceImpl<ProductDemandInfo, Long> implements ProductDemandInfoService {

    private static final Logger log = LoggerFactory.getLogger(ProductDemandInfoServiceImpl.class);

    @Resource
    private ProductDemandReplyInfoService productDemandReplyInfoService;

    private int batchSize = 100;

    @Resource
    private ProductDemandInfoDao productDemandInfoDao;

    @Override
    public BaseDao<ProductDemandInfo, Long> getDao() {
        return this.productDemandInfoDao;
    }

    @Override
    public Message<Page<ProductDemandInfo>> list(Long userId, Integer pageSize, Integer currentPage) {
        Page<ProductDemandInfo> page = new Page<>();
        ProductDemandInfo condition = new ProductDemandInfo();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        condition.setConsumerId(userId);
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        condition.setState(2);
        Page<ProductDemandInfo> productDemandInfoPage = this.selectPage(condition, page);
        List<ProductDemandInfo> data = null;
        if(productDemandInfoPage != null) data = productDemandInfoPage.getData();
        if(CollectionUtils.isNotEmpty(data)){
            data.stream().forEach(productDemandInfo -> {
                Long id = productDemandInfo.getId();
                Integer replyTotal = productDemandReplyInfoService.getReplyTotalByDemandId(id);
                productDemandInfo.setReplyTotal(replyTotal);
            });
        }
        return Message.createBySuccess(productDemandInfoPage);
    }

    @Override
    public void transBrowserTimesFromRedis2DB(List<BrowserTimesDto> browserTimesDtos) {
        if(CollectionUtils.isEmpty(browserTimesDtos)) return;
        Integer total = productDemandInfoDao.updateBrowseTimesByDtoList(browserTimesDtos);
        log.info("------->更新了{}条商品需求浏览记录", total);
    }

    @Override
    @ReleaseAnnotation(type = ReleaseType.DEMAND, value = "#p0", operationType = OperationType.REMOVE)
    public Message<String> deleteById(Long id, Long userId) {
        Long userIdByDemandId = productDemandInfoDao.selectUserIdByDemandId(id);
        if(userIdByDemandId == null) return Message.createByError("删除失败,请刷新后重试");
        if(!userId.equals(userIdByDemandId)){
            log.error("id为{}的用户试图删除id为{}的需求，但是该需求不属于他", userId, id);
            throw new GoodsException("非法操作,你当前的操作已经被记录");
        }
        ProductDemandInfo productDemandInfo = new ProductDemandInfo();
        productDemandInfo.setId(id);
        productDemandInfo.setState(1);
        productDemandInfoDao.updateByKey(productDemandInfo);
        return Message.createBySuccess();
    }

    @Override
    public Message<String> releaseDemand(ProductDemandInfo productDemandInfo, Long userId) {
        productDemandInfo.setConsumerId(userId);
        productDemandInfoDao.insertEntry(productDemandInfo);
        Long demandInfoId = productDemandInfo.getId();
        if(demandInfoId == null) return Message.createByError();
        productDemandInfo.setId(demandInfoId);
        convertInfo(new ESProductDto(productDemandInfo));
        return Message.createBySuccess();
    }

    @Override
    public void updateDemandCollectTotal(List<GoodsForwardDto> list) {
        productDemandInfoDao.updateCollectTotal(list);
    }

    @Override
    public Message<Page<ProductDemandInfo>> list(Integer pageSize, Integer currentPage) {
        ProductDemandInfo productDemandInfo = new ProductDemandInfo();
        productDemandInfo.setState(2);
        productDemandInfo.setOrderField("createTime");
        productDemandInfo.setOrderFieldType("DESC");
        Page<ProductDemandInfo> page = new Page<>();
        page = this.selectPage(productDemandInfo, page);
        return Message.createBySuccess(page);
    }

    @Override
    public List<ProductDemandInfo> getInfoByIds(List<Long> res) {
        return productDemandInfoDao.selectInfoByIds(res);
    }

    @Override
    public Message<ProductDemandInfo> detailById(Long id) {
        List<ProductDemandInfo> productDemandInfos = productDemandInfoDao.selectEntryList(id);
        if(productDemandInfos == null || productDemandInfos.isEmpty()) return Message.createByError("未找到相关信息");
        ProductDemandInfo demandInfo = productDemandInfos.get(0);
        Integer replyTotal = productDemandReplyInfoService.getReplyTotalByDemandId(demandInfo.getId());
        demandInfo.setReplyTotal(replyTotal);
        return Message.createBySuccess(demandInfo);
    }

    @ReleaseAnnotation(type = ReleaseType.GOODS, value = "#p0", operationType = OperationType.INSERT)
    private void convertInfo(ESProductDto esDto){}

}