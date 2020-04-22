package top.imuster.goods.service.impl;


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

}