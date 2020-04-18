package top.imuster.goods.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.goods.api.pojo.ProductCategoryInfo;

import java.util.List;

/**
 * ProductCategoryInfoDao 接口
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public interface ProductCategoryInfoDao extends BaseDao<ProductCategoryInfo, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 查询所有可用的分类信息
     * @Date: 2020/4/18 16:25
     * @param
     * @reture: java.util.List<top.imuster.goods.api.pojo.ProductCategoryInfo>
     **/
    List<ProductCategoryInfo> selectAllCategory();

}