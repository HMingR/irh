package top.imuster.goods.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.goods.api.pojo.ProductInfo;

/**
 * ProductInfoDao 接口
 * @author 黄明人
 * @since 2019-11-24 16:31:58
 */
public interface ProductInfoDao extends BaseDao<ProductInfo, Long> {
    //自定义扩展

    /**
     * @Description: 按条件更新商品的分类
     * @Author: hmr
     * @Date: 2019/12/22 15:28
     * @param productInfo
     * @reture: void
     **/
    Integer updateProductCategoryByCondition(ProductInfo productInfo);
}