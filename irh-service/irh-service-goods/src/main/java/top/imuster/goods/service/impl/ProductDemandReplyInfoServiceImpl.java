package top.imuster.goods.service.impl;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.cache.RedisCachePrefix;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductDemandReplyInfo;
import top.imuster.goods.dao.ProductDemandReplyInfoDao;
import top.imuster.goods.exception.GoodsException;
import top.imuster.goods.service.ProductDemandReplyInfoService;

import javax.annotation.Resource;

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
    @Cacheable(value = "#p1", key = "#p2 + '::page::' + #p1")
    public Message<Page<ProductDemandReplyInfo>> getFirstClassReplyListByPage(Integer pageSize, Integer currentPage, Long demandId) {
        Page<ProductDemandReplyInfo> page = new Page<>();
        ProductDemandReplyInfo condition = new ProductDemandReplyInfo();
        condition.setState(2);
        condition.setDemandId(demandId);
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        page.setSearchCondition(condition);
        page = this.selectPage(condition, page);
        return Message.createBySuccess(page);
    }

    @Override
    @Cacheable(value = RedisCachePrefix.DEMAND_REPLY_TOTAL, key = "'demandReplyTotal::' + #p0")
    public Integer getReplyTotalByDemandId(Long id) {
        return productDemandReplyInfoDao.selectReplyTotalByDemandId(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = RedisCachePrefix.DEMAND_REPLY_LIST, allEntries = true),
            @CacheEvict(value = RedisCachePrefix.DEMAND_REPLY_TOTAL, key = "#p0.id")
    })
    public Message<String> writeReply(ProductDemandReplyInfo replyInfo) {
        int i = productDemandReplyInfoDao.insertEntry(replyInfo);
        if(i != 1) throw new GoodsException("浏览失败");
        return Message.createBySuccess();
    }
}