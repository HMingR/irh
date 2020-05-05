package top.imuster.goods.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.goods.api.pojo.ProductDemandReplyInfo;

/**
 * ProductDemandReplyInfoDao 接口
 * @author 黄明人
 * @since 2020-05-03 15:01:34
 */
public interface ProductDemandReplyInfoDao extends BaseDao<ProductDemandReplyInfo, Long> {
    //自定义扩展

    /**
     * @Author hmr
     * @Description 根据demandId查询回复总数
     * @Date: 2020/5/4 19:24
     * @param id
     * @reture: java.lang.Integer
     **/
    Integer selectReplyTotalByDemandId(Long id);
}