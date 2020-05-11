package top.imuster.order.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.order.api.pojo.ProductEvaluateInfo;

/**
 * ProductEvaluateInfoDao 接口
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public interface ProductEvaluateInfoDao extends BaseDao<ProductEvaluateInfo, Long> {

    /**
     * @Author hmr
     * @Description 根据order 的id获得评价id
     * @Date: 2020/5/11 20:08
     * @param id
     * @reture: java.lang.Long
     **/
    Long selectIdByOrderId(Long id);
}