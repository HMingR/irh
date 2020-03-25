package top.imuster.life.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.life.api.pojo.ArticleTagRel;

import java.util.List;

/**
 * ArticleTagRelDao 接口
 * @author 黄明人
 * @since 2020-02-16 16:19:34
 */
public interface ArticleTagRelDao extends BaseDao<ArticleTagRel, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 根据文章id获得文章的标签名称
     * @Date: 2020/2/28 10:09
     * @param id
     * @reture: java.util.List<java.lang.String>
     **/
    List<String> selectTagNameByArticleId(Long id);

    /**
     * @Author hmr
     * @Description 根据tagids分页查询文章id
     * @Date: 2020/3/25 14:48
     * @param articleTagRel
     * @reture: java.util.List<java.lang.Long>
     **/
    List<Long> selectArticleIdByPageAndTagIds(ArticleTagRel articleTagRel);

}