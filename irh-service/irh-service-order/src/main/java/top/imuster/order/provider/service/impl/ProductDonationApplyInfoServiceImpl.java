package top.imuster.order.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.UserDto;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;
import top.imuster.order.provider.dao.ProductDonationApplyInfoDao;
import top.imuster.order.provider.service.ProductDonationApplyInfoService;

import javax.annotation.Resource;

/**
 * ProductDonationApplyInfoService 实现类
 * @author 黄明人
 * @since 2020-04-14 16:45:13
 */
@Service("productDonationApplyInfoService")
public class ProductDonationApplyInfoServiceImpl extends BaseServiceImpl<ProductDonationApplyInfo, Long> implements ProductDonationApplyInfoService {

    @Resource
    private ProductDonationApplyInfoDao productDonationApplyInfoDao;

    @Override
    public BaseDao<ProductDonationApplyInfo, Long> getDao() {
        return this.productDonationApplyInfoDao;
    }

    @Override
    public Message<String> apply(UserDto userInfo, ProductDonationApplyInfo applyInfo) {
        if(userInfo.getUserType().getType() != 40){
            return Message.createByError("权限不足");
        }
        applyInfo.setApplyUserId(userInfo.getUserId());
        productDonationApplyInfoDao.insertEntry(applyInfo);
        return Message.createBySuccess();
    }

    @Override
    public Message<String> approve(ProductDonationApplyInfo approveInfo) {
        productDonationApplyInfoDao.updateByKey(approveInfo);
        return Message.createBySuccess();
    }
}