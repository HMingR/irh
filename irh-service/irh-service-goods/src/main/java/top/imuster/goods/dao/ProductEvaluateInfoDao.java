package top.imuster.goods.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.goods.api.pojo.ProductEvaluateInfo;

/**
 * ProductEvaluateInfoDao 接口
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public interface ProductEvaluateInfoDao extends BaseDao<ProductEvaluateInfo, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 插入留言信息并返回id
     * @Date: 2020/4/29 10:19
     * @param evaluateInfo
     * @reture: java.lang.Integer
     **/
    Long insertInfoReturnId(ProductEvaluateInfo evaluateInfo);

}