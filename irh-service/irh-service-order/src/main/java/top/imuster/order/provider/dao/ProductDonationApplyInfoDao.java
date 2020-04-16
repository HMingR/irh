package top.imuster.order.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;

import java.util.HashMap;
import java.util.List;

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

    /**
     * @Author hmr
     * @Description 查看已经转账的订单的具体信息
     * @Date: 2020/4/16 8:58
     * @param param key: startIndex 和  pageSize
     * @reture: java.util.List<top.imuster.order.api.pojo.ProductDonationApplyInfo>
     **/
    List<ProductDonationApplyInfo> selectFinishApplyList(HashMap<String, Integer> param);

    /**
     * @Author hmr
     * @Description 查询一共有多少个已经转账的申请
     * @Date: 2020/4/16 9:34
     * @param
     * @reture: java.lang.Integer
     **/
    Integer selectApplyCountByState(Integer state);

    /**
     * @Author hmr
     * @Description 查看正在审核的申请
     * @Date: 2020/4/16 9:42
     * @param param
     * @reture: java.util.List<top.imuster.order.api.pojo.ProductDonationApplyInfo>
     **/
    List<ProductDonationApplyInfo> selectUnfinishApplyList(HashMap<String, Integer> param);
}