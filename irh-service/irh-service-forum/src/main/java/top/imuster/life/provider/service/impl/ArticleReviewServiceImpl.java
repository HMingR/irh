package top.imuster.life.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleReviewInfo;
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
public class ArticleReviewServiceImpl extends BaseServiceImpl<ArticleReviewInfo, Long> implements ArticleReviewService {

    @Resource
    private ArticleReviewDao articleReviewDao;

    @Resource
    UserForumAttributeService userForumAttributeService;

    @Override
    public BaseDao<ArticleReviewInfo, Long> getDao() {
        return this.articleReviewDao;
    }

    @Override
    public List<ArticleReviewInfo> reviewDetails(Page<ArticleReviewInfo> page, Long userId) {
        ArticleReviewInfo searchCondition = page.getSearchCondition();
        searchCondition.setState(2);

        //按照降序排列
        searchCondition.setOrderField("create_time");
        searchCondition.setOrderFieldType("ASC");
        Page<ArticleReviewInfo> articleReviewPage = this.selectPage(searchCondition, page);
        List<ArticleReviewInfo> res = articleReviewPage.getData();

        //将回复转换成一个map，这个map里面key为parentId，value为用户id
        Map<Long, Long> collect = res.stream().collect(Collectors.toMap(ArticleReviewInfo::getId, ArticleReviewInfo::getUserId));
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
    public Message<Page<ArticleReviewInfo>> list(Long userId, Integer pageSize, Integer currentPage) {
        ArticleReviewInfo articleReviewInfo = new ArticleReviewInfo();
        articleReviewInfo.setUserId(userId);
        articleReviewInfo.setState(2);
        articleReviewInfo.setOrderField("create_time");
        articleReviewInfo.setOrderFieldType("DESC");
        articleReviewInfo.setFirstClassId(0L);
        articleReviewInfo.setParentId(0L);
        Page<ArticleReviewInfo> page = new Page<>();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        page.setSearchCondition(articleReviewInfo);
        page = this.selectPage(articleReviewInfo, page);
        return Message.createBySuccess(page);
    }

    @Override
    public Long getUserIdByArticleReviewId(Long targetId) {
        return articleReviewDao.selectUserIdByReviewId(targetId);
    }

    @Override
    public List<ArticleReviewInfo> getUpTotalByIds(Long[] reviewIds) {
        return articleReviewDao.selectUpTotalByIds(reviewIds);
    }

    @Override
    public Long getUpTotal(Long id) {
        return articleReviewDao.selectUpTotalById(id);
    }

    @Override
    public Message<List<ArticleReviewInfo>> selectFirstClassReviewListByArticleId(Long articleId, Integer currentPage, Integer pageSize, Long userId) {
        ArticleReviewInfo searchCondition = new ArticleReviewInfo();
        searchCondition.setStartIndex((currentPage-1) * pageSize);
        searchCondition.setEndIndex(pageSize);
        searchCondition.setArticleId(articleId);
        List<ArticleReviewInfo> data = articleReviewDao.selectFirstClassReviewByArticleId(searchCondition);

        ArticleReviewInfo review = new ArticleReviewInfo();
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
        List<ArticleReviewInfo> collect = data.stream().sorted(Comparator.comparingLong(ArticleReviewInfo::getUpTotal).reversed()).collect(Collectors.toList());
        return Message.createBySuccess(collect);
    }
}