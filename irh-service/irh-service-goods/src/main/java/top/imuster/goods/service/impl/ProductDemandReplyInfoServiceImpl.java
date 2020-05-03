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
import java.util.Map;
import java.util.stream.Collectors;

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
        condition.setFirstClassId(0L);
        condition.setParentId(0L);
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        page.setSearchCondition(condition);
        page = this.selectPage(condition, page);
        List<ProductDemandReplyInfo> data = page.getData();

        condition.setParentId(null);
        condition.setFirstClassId(null);
        if(data != null && !data.isEmpty()){
            data.stream().forEach(productDemandReplyInfo -> {
                condition.setFirstClassId(productDemandReplyInfo.getId());
                Integer childTotal = productDemandReplyInfoDao.selectEntryListCount(condition);
                productDemandReplyInfo.setChildTotal(childTotal);
            });
        }
        return Message.createBySuccess(page);
    }

    @Override
    public Message<Page<ProductDemandReplyInfo>> getReplyChildById(Integer pageSize, Integer currentPage, Long replyId) {
        Page<ProductDemandReplyInfo> page = new Page<>();
        page.setPageSize(pageSize);
        page.setCurrentPage(currentPage);
        ProductDemandReplyInfo condition = new ProductDemandReplyInfo();
        condition.setFirstClassId(replyId);
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        condition.setState(2);
        page = this.selectPage(condition, page);

        List<ProductDemandReplyInfo> data = page.getData();
        if(data != null && !data.isEmpty()){
            Map<Long, Long> collect = data.stream().collect(Collectors.toMap(ProductDemandReplyInfo::getId, ProductDemandReplyInfo::getUserId));
            data.stream().forEach(productDemandReplyInfo -> {
                Long id = productDemandReplyInfo.getId();
                Long parentWriteUserId = collect.get(id);
                if(parentWriteUserId != null) productDemandReplyInfo.setParentWriterId(parentWriteUserId);
            });
        }
        return Message.createBySuccess(page);
    }
}