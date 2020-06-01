package top.imuster.goods.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.goods.api.pojo.ProductRotationImgInfo;
import top.imuster.goods.dao.ProductRotationImgInfoDao;
import top.imuster.goods.service.ProductRotationImgInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ProductRotationImgInfoService 实现类
 * @author 黄明人
 * @since 2020-06-01 15:45:21
 */
@Service("productRotationImgInfoService")
public class ProductRotationImgInfoServiceImpl extends BaseServiceImpl<ProductRotationImgInfo, Long> implements ProductRotationImgInfoService {

    @Resource
    private ProductRotationImgInfoDao productRotationImgInfoDao;

    @Override
    public BaseDao<ProductRotationImgInfo, Long> getDao() {
        return this.productRotationImgInfoDao;
    }

    @Override
    public Integer saveClick2DB(List<BrowserTimesDto> res) {
        return productRotationImgInfoDao.updateClickTotal(res);
    }
}