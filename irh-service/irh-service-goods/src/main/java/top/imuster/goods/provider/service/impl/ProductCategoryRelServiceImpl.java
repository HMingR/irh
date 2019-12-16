package top.imuster.goods.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.goods.api.pojo.ProductCategoryRel;
import top.imuster.goods.provider.dao.ProductCategoryRelDao;
import top.imuster.goods.provider.service.ProductCategoryRelService;

import javax.annotation.Resource;

/**
 * ProductCategoryRelService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productCategoryRelService")
public class ProductCategoryRelServiceImpl extends BaseServiceImpl<ProductCategoryRel, Long> implements ProductCategoryRelService {

    @Resource
    private ProductCategoryRelDao productCategoryRelDao;

    @Override
    public BaseDao<ProductCategoryRel, Long> getDao() {
        return this.productCategoryRelDao;
    }
}