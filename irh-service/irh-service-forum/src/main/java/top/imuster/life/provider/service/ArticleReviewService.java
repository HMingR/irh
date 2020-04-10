package top.imuster.life.provider.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleReviewInfo;

import java.util.List;

/**
 * ArticleReviewService接口
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public interface ArticleReviewService extends BaseService<ArticleReviewInfo, Long> {

    /**
     * @Author hmr
     * @Description 根据一级留言的id获得其下所有的回复或留言
     * @Date: 2020/2/2 11:20
     * @param page
     * @param userId
     * @reture: java.util.List<ArticleReviewInfo>
     **/
    List<ArticleReviewInfo> reviewDetails(Page<ArticleReviewInfo> page, Long userId);

    /**
     * @Author hmr
     * @Description 用户查看自己的留言记录
     * @Date: 2020/2/3 11:02
     * @param userId
     * @param pageSize
     * @param currentPage
     * @reture: java.util.List<ArticleReviewInfo>
     * @return*/
    Message<Page<ArticleReviewInfo>> list(Long userId, Integer pageSize, Integer currentPage);

    /**
     * @Author hmr
     * @Description 根据留言id获得留言者的id
     * @Date: 2020/2/5 10:43
     * @param targetId
     * @reture: java.lang.Long
     **/
    Long getUserIdByArticleReviewId(Long targetId);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/2/8 20:41
     * @param reviewIds
     * @reture: java.util.List<ArticleReviewInfo>
     **/
    List<ArticleReviewInfo> getUpTotalByIds(Long[] reviewIds);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/2/9 10:41
     * @param id
     * @reture: java.lang.Long
     **/
    Long getUpTotal(Long id);

    /**
     * @Author hmr
     * @Description 根据文章id分页查询一级留言的信息
     * @Date: 2020/3/15 9:53
     * @param page
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.life.api.pojo.ArticleReviewInfo>>
     **/
    Message<List<ArticleReviewInfo>> selectFirstClassReviewListByArticleId(Long articleId, Integer currentPage, Integer pageSize, Long userId);
}