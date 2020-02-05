package top.imuster.forum.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.forum.api.pojo.ArticleReview;

import java.util.List;

/**
 * ArticleReviewService接口
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public interface ArticleReviewService extends BaseService<ArticleReview, Long> {

    /**
     * @Author hmr
     * @Description 根据文章的id获得该文章下所有的一级留言
     * @Date: 2020/2/2 11:05
     * @param id
     * @reture: java.util.List<top.imuster.forum.api.pojo.ArticleReview>
     **/
    List<ArticleReview> getFirstClassReviewInfoById(Long id);

    /**
     * @Author hmr
     * @Description 根据一级留言的id获得其下所有的回复或留言
     * @Date: 2020/2/2 11:20
     * @param id
     * @reture: java.util.List<top.imuster.forum.api.pojo.ArticleReview>
     **/
    List<ArticleReview> reviewDetails(Long id);

    /**
     * @Author hmr
     * @Description 用户查看自己的留言记录
     * @Date: 2020/2/3 11:02
     * @param userId
     * @reture: java.util.List<top.imuster.forum.api.pojo.ArticleReview>
     **/
    List<ArticleReview> list(Long userId);

    /**
     * @Author hmr
     * @Description 根据帖子回复id获得发布者id
     * @Date: 2020/2/5 10:43
     * @param targetId
     * @reture: java.lang.Long
     **/
    Long getUserIdByArticleReviewId(Long targetId);
}