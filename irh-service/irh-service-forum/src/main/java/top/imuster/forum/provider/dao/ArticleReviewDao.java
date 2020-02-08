package top.imuster.forum.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.forum.api.pojo.ArticleReview;

import java.util.List;

/**
 * ArticleReviewDao 接口
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public interface ArticleReviewDao extends BaseDao<ArticleReview, Long> {
    //自定义扩展

    /**
     * @Author hmr
     * @Description 根据回复id获得用户id
     * @Date: 2020/2/5 10:44
     * @param targetId
     * @reture: java.lang.Long
     **/
    Long selectUserIdByReviewId(Long targetId);


    /**
     * @Author hmr
     * @Description
     * @Date: 2020/2/8 20:42
     * @param reviewIds
     * @reture: java.util.List<top.imuster.forum.api.pojo.ArticleReview>
     **/
    List<ArticleReview> selectUpTotalByIds(Long[] reviewIds);
}