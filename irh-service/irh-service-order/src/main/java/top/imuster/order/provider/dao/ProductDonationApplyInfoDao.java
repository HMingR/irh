package top.imuster.order.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;

/**
 * ProductDonationApplyInfoDao 接口
 * @author 黄明人
 * @since 2020-04-14 16:45:13
 */
public interface ProductDonationApplyInfoDao extends BaseDao<ProductDonationApplyInfo, Long> {
    //自定义扩展

    /**
     * @Author hmr
     * @Description 根据id查询可以发放金额的申请信息
     * @Date: 2020/4/15 9:24
     * @param id
     * @reture: top.imuster.order.api.pojo.ProductDonationApplyInfo
     **/
    ProductDonationApplyInfo selectAvailableApplyById(Long id);
}