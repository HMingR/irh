package top.imuster.mall.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.mall.dao.ProductCategoryRelDao;
import top.imuster.mall.domain.ProductCategoryRel;
import top.imuster.mall.service.ProductCategoryRelService;
import top.imuster.service.base.BaseServiceImpl;

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