package top.imuster.goods.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.goods.api.pojo.ProductCategoryInfo;
import top.imuster.goods.config.GoodsException;

import java.util.List;

/**
 * ProductCategoryInfoService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface ProductCategoryInfoService extends BaseService<ProductCategoryInfo, Long> {

    /**
     * @Description: 生成商品分类树
     * @Author: hmr
     * @Date: 2019/12/22 12:13
     * @param
     * @reture: java.util.List<top.imuster.goods.api.pojo.ProductCategoryInfo>
     **/
    List<ProductCategoryInfo> getCategoryTree() throws GoodsException;

    /**
     * @Description: 通过id删除分类,并且删除该分类下所有的子节点，并且被标识为该分类的所有商品的分类上升为被删除的父节点
     * @Author: hmr
     * @Date: 2019/12/22 15:16
     * @param id
     * @reture: void
     **/
    Integer delCategoryById(Long id) throws GoodsException;

}