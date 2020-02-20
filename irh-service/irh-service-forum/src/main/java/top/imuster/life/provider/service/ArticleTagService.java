package top.imuster.life.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.life.api.pojo.ArticleTag;

import java.util.List;

/**
 * ArticleCategoryService接口
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public interface ArticleTagService extends BaseService<ArticleTag, Long> {

    /**
     * @Author hmr
     * @Description 根据分类id获得标签
     * @Date: 2020/2/3 11:14
     * @param
     * @reture: java.util.List<top.imuster.forum.api.pojo.ArticleCategory>
     **/
    List<ArticleTag> getTagByCategoryId(Long id);
}