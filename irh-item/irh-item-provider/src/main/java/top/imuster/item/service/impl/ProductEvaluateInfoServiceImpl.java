package top.imuster.item.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.item.dao.ProductEvaluateInfoDao;
import top.imuster.item.domain.ProductEvaluateInfo;
import top.imuster.item.service.ProductEvaluateInfoService;
import top.imuster.service.base.BaseServiceImpl;

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