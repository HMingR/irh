package top.imuster.goods.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductDemandReplyInfo;
import top.imuster.goods.dao.ProductDemandReplyInfoDao;
import top.imuster.goods.service.ProductDemandReplyInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ProductDemandReplyInfoService 实现类
 * @author 黄明人
 * @since 2020-05-03 15:01:34
 */
@Service("productDemandReplyInfoService")
public class ProductDemandReplyInfoServiceImpl extends BaseServiceImpl<ProductDemandReplyInfo, Long> implements ProductDemandReplyInfoService {

    @Resource
    private ProductDemandReplyInfoDao productDemandReplyInfoDao;

    @Override
    public BaseDao<ProductDemandReplyInfo, Long> getDao() {
        return this.productDemandReplyInfoDao;
    }

    @Override
    public Message<Page<ProductDemandReplyInfo>> getFirstClassReplyListByPage(Integer pageSize, Integer currentPage, Long demandId) {
        Page<ProductDemandReplyInfo> page = new Page<>();
        ProductDemandReplyInfo condition = new ProductDemandReplyInfo();
        condition.setState(2);
        condition.setDemandId(demandId);
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        page.setSearchCondition(condition);
        page = this.selectPage(condition, page);
        List<ProductDemandReplyInfo> data = page.getData();

        if(data != null && !data.isEmpty()){
            data.stream().forEach(productDemandReplyInfo -> {
                Integer childTotal = productDemandReplyInfoDao.selectEntryListCount(condition);
                productDemandReplyInfo.setChildTotal(childTotal);
            });
        }
        return Message.createBySuccess(page);
    }

    @Override
    public Integer getReplyTotalByDemandId(Long id) {
        return productDemandReplyInfoDao.selectReplyTotalByDemandId(id);
    }
}