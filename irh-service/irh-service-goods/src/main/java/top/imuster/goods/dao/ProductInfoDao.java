package top.imuster.goods.dao;


import org.apache.ibatis.annotations.Param;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.goods.api.pojo.ProductInfo;

import java.util.List;
import java.util.Map;

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
     * @Description 根据商品id获得商品的简略信息
     * @Date: 2020/2/7 16:50
     * @param id
     * @reture: java.lang.String
     **/
    ProductInfo selectProductBriefInfoById(Long id);


    /**
     * @Author hmr
     * @Description 根据id查询对应的浏览记录
     * @Date: 2020/4/22 9:42
     * @param ids
     * @reture: java.util.Map<java.lang.Long,java.lang.Long>
     **/
    Map<Long, Long> selectBrowserTimesByIds(@Param("ids") Long[] ids);

    /**
     * @Author hmr
     * @Description 根据id更新浏览记录
     * @Date: 2020/4/22 9:51
     * @param update
     * @reture: void
     **/
    void updateBrowserTimesByCondition(@Param("update") List<ProductInfo> update);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/5/3 16:26
     * @param id
     * @reture: java.lang.Long
     **/
    Long selectUserIdByProductId(Long id);
}