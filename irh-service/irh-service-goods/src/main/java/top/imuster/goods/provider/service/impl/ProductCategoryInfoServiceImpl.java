package top.imuster.goods.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.goods.api.pojo.ProductCategoryInfo;
import top.imuster.goods.provider.dao.ProductCategoryInfoDao;
import top.imuster.goods.provider.service.ProductCategoryInfoService;

import javax.annotation.Resource;

/**
 * ProductCategoryInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productCategoryInfoService")
public class ProductCategoryInfoServiceImpl extends BaseServiceImpl<ProductCategoryInfo, Long> implements ProductCategoryInfoService {

    @Resource
    private ProductCategoryInfoDao productCategoryInfoDao;

    @Override
    public BaseDao<ProductCategoryInfo, Long> getDao() {
        return this.productCategoryInfoDao;
    }
}