package top.imuster.life.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.forum.api.pojo.ArticleCollection;

import java.util.List;

/**
 * ArticleCollectionDao 接口
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
public interface ArticleCollectionDao extends BaseDao<ArticleCollection, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 查看收藏列表
     * @Date: 2020/2/11 15:39
     * @param searchCondition
     * @reture: java.util.List<top.imuster.forum.api.pojo.ArticleInfo>
     **/
    List<ArticleCollection> selectCollectByCondition(ArticleCollection searchCondition);

    /**
     * @Author hmr
     * @Description 根据用户id获得该用户收藏了多少文章
     * @Date: 2020/2/15 15:39
     * @param userId
     * @reture: java.lang.Long
     **/
    Long selectTotalByUserId(Long userId);
}