package top.imuster.life.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleTagInfo;

import java.util.List;

/**
 * ArticleCategoryService接口
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public interface ArticleTagService extends BaseService<ArticleTagInfo, Long> {

    /**
     * @Author hmr
     * @Description 根据分类id获得标签
     * @Date: 2020/2/3 11:14
     * @param
     * @reture: java.util.List<top.imuster.forum.api.pojo.ArticleCategory>
     **/
    List<Long> getTagByCategoryId(Long id);

    /**
     * @Author hmr
     * @Description 根据用户关注的兴趣标签id获得文章标签
     * @Date: 2020/4/9 9:25
     * @param ids
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<java.lang.Long>>
     **/
    Message<List<ArticleTagInfo>> getTagByCategoryIds(String ids);
}