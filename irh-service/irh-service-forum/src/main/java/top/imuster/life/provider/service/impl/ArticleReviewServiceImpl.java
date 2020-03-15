package top.imuster.life.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleReview;
import top.imuster.life.provider.dao.ArticleReviewDao;
import top.imuster.life.provider.service.ArticleReviewService;
import top.imuster.life.provider.service.UserForumAttributeService;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ArticleReviewService 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Service("articleReviewService")
public class ArticleReviewServiceImpl extends BaseServiceImpl<ArticleReview, Long> implements ArticleReviewService {

    @Resource
    private ArticleReviewDao articleReviewDao;

    @Resource
    UserForumAttributeService userForumAttributeService;

    @Override
    public BaseDao<ArticleReview, Long> getDao() {
        return this.articleReviewDao;
    }

    @Override
    public List<ArticleReview> reviewDetails(Page<ArticleReview> page, Long userId) {
        ArticleReview searchCondition = page.getSearchCondition();
        searchCondition.setState(2);

        //按照降序排列
        searchCondition.setOrderField("create_time");
        searchCondition.setOrderFieldType("ASC");
        Page<ArticleReview> articleReviewPage = this.selectPage(searchCondition, page);
        List<ArticleReview> res = articleReviewPage.getData();

        //将回复转换成一个map，这个map里面key为parentId，value为用户id
        Map<Long, Long> collect = res.stream().collect(Collectors.toMap(ArticleReview::getId, ArticleReview::getUserId));
        res.stream().forEach(condition -> {

            //如果userId不为空的话则显示出当前用户是否点赞了该留言
            if(userId != null){
                Message<Integer> state = userForumAttributeService.getStateByTargetId(2, condition.getId(), userId);
                condition.setUpState(state.getData());
            }

            //将每个留言的父级用户id标记出来
            Long parentId = condition.getParentId();
            if(parentId != null){
                Long writerId = collect.get(parentId);
                if(writerId != null){
                    condition.setParentWriterId(writerId);
                }
            }
        });
        return res;
    }

    @Override
    public List<ArticleReview> list(Long userId) {
        ArticleReview articleReview = new ArticleReview();
        articleReview.setUserId(userId);
        articleReview.setState(2);
        articleReview.setOrderField("create_time");
        articleReview.setOrderFieldType("DESC");
        articleReview.setFirstClassId(0l);
        articleReview.setParentId(0l);
        return articleReviewDao.selectEntryList(articleReview);
    }

    @Override
    public Long getUserIdByArticleReviewId(Long targetId) {
        return articleReviewDao.selectUserIdByReviewId(targetId);
    }

    @Override
    public List<ArticleReview> getUpTotalByIds(Long[] reviewIds) {
        return articleReviewDao.selectUpTotalByIds(reviewIds);
    }

    @Override
    public Long getUpTotal(Long id) {
        return articleReviewDao.selectUpTotalById(id);
    }

    @Override
    public Message<Page<ArticleReview>> selectFirstClassReviewListByArticleId(Page<ArticleReview> page,final Long userId) {
        ArticleReview searchCondition = page.getSearchCondition();
        searchCondition.setFirstClassId(0L);
        searchCondition.setState(2);
        Page<ArticleReview> res = this.selectPage(page.getSearchCondition(), page);

        final Long articleId = page.getSearchCondition().getArticleId();
        List<ArticleReview> data = res.getData();

        ArticleReview review = new ArticleReview();
        review.setState(2);
        data.stream().forEach(articleReview -> {
            //获得一级留言下的所有回复数量
            review.setFirstClassId(articleReview.getId());
            Integer count = articleReviewDao.selectEntryListCount(review);
            articleReview.setChildTotalCount(count);
            if(userId != null){
                Message<Integer> state = userForumAttributeService.getStateByTargetId(2, articleId, userId);
                articleReview.setUpState(state.getData());
            }
        });

        //按照点赞多少进行降序排列
        List<ArticleReview> collect = data.stream().sorted(Comparator.comparingLong(ArticleReview::getUpTotal).reversed()).collect(Collectors.toList());
        page.setData(collect);
        return Message.createBySuccess(res);
    }
}