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

   /**
    * @Author hmr
    * @Description 根据id查询卖家id
    * @Date: 2020/1/21 10:47
    * @param id
    * @reture: java.lang.String
    **/
   Long selectSalerIdByProductId(Long id);

    /**
     * @Author hmr
     * @Description 根据留言id查询所属商品
     * @Date: 2020/1/22 11:37
     * @param id
     * @reture: top.imuster.goods.api.pojo.ProductInfo
     **/
    ProductInfo selectProductInfoByMessageId(Long id);

    /**
     * @Author hmr
     * @Description 根据商品id获得商品图片的uri
     * @Date: 2020/2/7 16:50
     * @param id
     * @reture: java.lang.String
     **/
    String selectMainPicUrlById(Long id);
}