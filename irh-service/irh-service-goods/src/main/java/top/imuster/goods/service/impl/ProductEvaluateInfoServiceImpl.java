package top.imuster.goods.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.goods.api.pojo.ProductEvaluateInfo;
import top.imuster.goods.dao.ProductEvaluateInfoDao;
import top.imuster.goods.service.ProductEvaluateInfoService;

import javax.annotation.Resource;

/**
 * ProductEvaluateInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productEvaluateInfoService")
public class ProductEvaluateInfoServiceImpl extends BaseServiceImpl<ProductEvaluateInfo, Long> implements ProductEvaluateInfoService {

    @Resource
    private ProductEvaluateInfoDao productEvaluateInfoDao;

    @Override
    public BaseDao<ProductEvaluateInfo, Long> getDao() {
        return this.productEvaluateInfoDao;
    }
}