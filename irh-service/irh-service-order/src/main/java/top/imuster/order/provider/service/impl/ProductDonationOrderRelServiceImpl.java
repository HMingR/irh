package top.imuster.order.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.pojo.ProductDonationOrderRel;
import top.imuster.order.provider.dao.ProductDonationOrderRelDao;
import top.imuster.order.provider.service.ProductDonationOrderRelService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ProductDonationOrderRelService 实现类
 * @author 黄明人
 * @since 2020-04-14 16:45:13
 */
@Service("productDonationOrderRelService")
public class ProductDonationOrderRelServiceImpl extends BaseServiceImpl<ProductDonationOrderRel, Long> implements ProductDonationOrderRelService {

    @Resource
    private ProductDonationOrderRelDao productDonationOrderRelDao;

    @Override
    public BaseDao<ProductDonationOrderRel, Long> getDao() {
        return this.productDonationOrderRelDao;
    }

    @Override
    public List<OrderInfo> getOrderInfoByApplyId(Long applyId) {
        return productDonationOrderRelDao.selectOrderListByFinishApplyId(applyId);
    }
}