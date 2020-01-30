package top.imuster.forum.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.forum.api.pojo.ArticleReview;
import top.imuster.forum.provider.dao.ArticleReviewDao;
import top.imuster.forum.provider.service.ArticleReviewService;

import javax.annotation.Resource;

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
}