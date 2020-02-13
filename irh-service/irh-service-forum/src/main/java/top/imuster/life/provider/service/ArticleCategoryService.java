package top.imuster.life.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.forum.api.pojo.ArticleCategory;

import java.util.List;

/**
 * ArticleCategoryService接口
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public interface ArticleCategoryService extends BaseService<ArticleCategory, Long> {

    /**
     * @Author hmr
     * @Description 获得分类树
     * @Date: 2020/2/3 11:14
     * @param
     * @reture: java.util.List<top.imuster.forum.api.pojo.ArticleCategory>
     **/
    List<ArticleCategory> getCategoryTree();

}