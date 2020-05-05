package top.imuster.goods.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.ProductDemandInfoDao;
import top.imuster.goods.service.ProductDemandInfoService;
import top.imuster.goods.service.ProductDemandReplyInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
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
        List<ProductDemandInfo> data = productDemandInfoPage.getData();
        data.stream().forEach(productDemandInfo -> {
            Long id = productDemandInfo.getId();
            Integer replyTotal = productDemandReplyInfoService.getReplyTotalByDemandId(id);
            productDemandInfo.setReplyTotal(replyTotal);
        });
        return Message.createBySuccess(productDemandInfoPage);
    }

    @Override
    public void transBrowserTimesFromRedis2DB(List<BrowserTimesDto> browserTimesDtos) {
        if(browserTimesDtos == null || browserTimesDtos.isEmpty()) return;
        HashSet<Long> targetIds = new HashSet<>();
        HashSet<Long> times = new HashSet<>();
        browserTimesDtos.stream().forEach(browserTimesDto -> {
            Long targetId = browserTimesDto.getTargetId();
            Long total = browserTimesDto.getTotal();
            targetIds.add(targetId);
            times.add(total);
        });
        Long[] ids = targetIds.toArray(new Long[targetIds.size()]);
        Long[] totals = times.toArray(new Long[times.size()]);
        for (int i = 0; i <= ids.length / batchSize; i++){
            ArrayList<ProductInfo> update = new ArrayList<>();
            Long[] selectIds = new Long[batchSize];
            for (int j = i * batchSize, x = 0; j < (i + 1) * batchSize; j++, x++) {
                selectIds[x] = ids[j];
                if(j == ids.length - 1) break;
            }
            for (int z = 0; z < selectIds.length; z++){
                if(selectIds[z] == null || selectIds[z] == 0) break;
                Long selectId = selectIds[z];
                Long total = totals[i * batchSize + z];
                ProductInfo condition = new ProductInfo();
                condition.setId(selectId);
                condition.setBrowserTimes(total);
                update.add(condition);
            }
            productDemandInfoDao.updateBrowserTimesByCondition(update);
        }
    }

    @Override
    public Message<String> deleteById(Long id, Long userId) {
        Long userIdByDemandId = productDemandInfoDao.selectUserIdByDemandId(id);
        if(userIdByDemandId == null) return Message.createByError("删除失败,请刷新后重试");
        if(!userId.equals(userIdByDemandId)){
            log.error("id为{}的用户试图删除id为{}的需求，但是该需求不属于他", userId, id);
            return Message.createByError("非法操作,你当前的操作已经被记录");
        }
        ProductDemandInfo productDemandInfo = new ProductDemandInfo();
        productDemandInfo.setId(id);
        productDemandInfo.setState(1);
        productDemandInfoDao.updateByKey(productDemandInfo);
        return Message.createBySuccess();
    }

}