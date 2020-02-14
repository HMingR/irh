package top.imuster.life.provider.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.dto.UserDto;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.forum.api.pojo.ArticleReview;
import top.imuster.forum.api.pojo.ForumHotTopic;
import top.imuster.life.provider.dao.ArticleInfoDao;
import top.imuster.life.provider.exception.ForumException;
import top.imuster.life.provider.service.ArticleInfoService;
import top.imuster.life.provider.service.ArticleReviewService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ArticleInfoService 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Service("articleInfoService")
@Slf4j
public class ArticleInfoServiceImpl extends BaseServiceImpl<ArticleInfo, Long> implements ArticleInfoService {

    @Resource
    private ArticleInfoDao articleInfoDao;

    @Resource
    private ArticleReviewService articleReviewService;

    @Override
    public BaseDao<ArticleInfo, Long> getDao() {
        return this.articleInfoDao;
    }

    @Override
    public void release(UserDto currentUser, ArticleInfo articleInfo) {
        articleInfo.setUserId(currentUser.getUserId());
        int i = articleInfoDao.insertEntry(articleInfo);
        if(i == 0){
            log.error("用户发布帖子出现异常,存入数据库的时候返回值为0");
            throw new ForumException("在发布的过程中服务器出现异常,发布结果未知");
        }
    }

    @Override
    public List<ArticleInfo> list(Page<ArticleInfo> page) {
        return articleInfoDao.selectListByCondition(page.getSearchCondition());
    }

    @Override
    public ArticleInfo getArticleDetailById(Long id) {
        ArticleInfo result = articleInfoDao.selectEntryList(id).get(0);

        //获得一级留言信息和其对应的回复总数
        List<ArticleReview> review = articleReviewService.getFirstClassReviewInfoById(id);
        result.setChilds(review);
        return result;
    }

    @Override
    public Long getUserIdByArticleId(Long targetId) {
        ArticleInfo condition = new ArticleInfo();
        condition.setId(targetId);
        condition.setState(2);
        ArticleInfo articleInfo = articleInfoDao.selectListByCondition(condition).get(0);
        return articleInfo.getUserId();
    }

    @Override
    public List<ArticleInfo> getUpTotalByIds(Long[] ids) {
        return articleInfoDao.selectUpTotalByIds(ids);
    }

    @Override
    public Long getUpTotal(Long id) {
        return articleInfoDao.selectUpTotalById(id);
    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "#p0")
    public ArticleInfo getBriefById(Long id) {
        return articleInfoDao.selectBriefById(id);
    }

    @Override
    public List<ArticleInfo> hotTopicListByCategory(Long id) {
        return null;
    }

    @Override
    public List<ArticleInfo> selectInfoByTargetIds(Long[] longs) {
        return articleInfoDao.selectInfoByTargetIds(longs);
    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_HOT_TOPIC_CACHE_KEY, key = "#p0")
    public ArticleInfo getBriefByHotTopicId(Long aLong) {
        return this.getBriefById(aLong);
    }
}