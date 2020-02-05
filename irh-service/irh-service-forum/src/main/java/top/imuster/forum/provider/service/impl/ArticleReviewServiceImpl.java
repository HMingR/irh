package top.imuster.forum.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.forum.api.pojo.ArticleReview;
import top.imuster.forum.provider.dao.ArticleReviewDao;
import top.imuster.forum.provider.service.ArticleReviewService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ArticleReviewService 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Service("articleReviewService")
public class ArticleReviewServiceImpl extends BaseServiceImpl<ArticleReview, Long> implements ArticleReviewService {

    @Resource
    private ArticleReviewDao articleReviewDao;

    @Override
    public BaseDao<ArticleReview, Long> getDao() {
        return this.articleReviewDao;
    }

    @Override
    public List<ArticleReview> getFirstClassReviewInfoById(Long id) {
        ArticleReview condition = new ArticleReview();
        condition.setState(2);
        condition.setArticleId(id);
        List<ArticleReview> articleReviews = articleReviewDao.selectEntryList(condition);

        //查询每个一级留言信息下的回复总数
        ArticleReview review = new ArticleReview();
        review.setState(2);
        articleReviews.stream().forEach(articleReview -> {
            review.setParentId(articleReview.getParentId());
            Integer count = articleReviewDao.selectEntryListCount(review);
            articleReview.setChildTotalCount(count);
        });

        return articleReviews;
    }

    @Override
    public List<ArticleReview> reviewDetails(Long id) {
        ArticleReview articleReview = new ArticleReview();
        articleReview.setState(2);
        articleReview.setFirstClassId(id);
        return articleReviewDao.selectEntryList(articleReview);
    }

    @Override
    public List<ArticleReview> list(Long userId) {
        ArticleReview articleReview = new ArticleReview();
        articleReview.setUserId(userId);
        articleReview.setState(2);
        articleReview.setOrderField("create_time");
        articleReview.setOrderFieldType("DESC");
        return articleReviewDao.selectEntryList(articleReview);
    }

    @Override
    public Long getUserIdByArticleReviewId(Long targetId) {
        return articleReviewDao.selectUserIdByReviewId(targetId);
    }
}