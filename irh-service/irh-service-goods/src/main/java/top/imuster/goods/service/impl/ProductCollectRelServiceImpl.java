package top.imuster.goods.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.goods.api.pojo.ProductCollectRel;
import top.imuster.goods.dao.ProductCollectRelDao;
import top.imuster.goods.service.ProductCollectRelService;

import javax.annotation.Resource;

/**
 * ProductCollectRelService 实现类
 * @author 黄明人
 * @since 2020-04-26 15:48:44
 */
@Service("productCollectRelService")
public class ProductCollectRelServiceImpl extends BaseServiceImpl<ProductCollectRel, Long> implements ProductCollectRelService {

    @Resource
    private ProductCollectRelDao productCollectRelDao;

    @Override
    public BaseDao<ProductCollectRel, Long> getDao() {
        return this.productCollectRelDao;
    }
}