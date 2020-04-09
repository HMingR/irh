package top.imuster.life.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.life.api.pojo.ArticleTagInfo;

import java.util.List;

/**
 * ArticleCategoryDao 接口
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public interface ArticleTagDao extends BaseDao<ArticleTagInfo, Long> {
    //自定义扩展

    /**
     * @Author hmr
     * @Description 根据分类id获得所有的标签id
     * @Date: 2020/3/25 14:24
     * @param categoryId
     * @reture: java.util.List<java.lang.Long>
     **/
    List<Long> selectTagIdByCategory(Long categoryId);

    /**
     * @Author hmr
     * @Description 根据分类id获得标签信息
     * @Date: 2020/4/9 9:30
     * @param sids
     * @reture: java.util.List<top.imuster.life.api.pojo.ArticleTagInfo>
     **/
    List<ArticleTagInfo> selectTagInfoByCategoryIds(List<String> list);
}