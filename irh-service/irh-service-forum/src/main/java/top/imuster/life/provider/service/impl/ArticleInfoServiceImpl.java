package top.imuster.life.provider.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.dto.UserDto;
import top.imuster.life.api.dto.ForwardDto;
import top.imuster.life.api.dto.UserBriefDto;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.pojo.ArticleTagInfo;
import top.imuster.life.api.pojo.ArticleTagRel;
import top.imuster.life.api.pojo.ForumHotTopicInfo;
import top.imuster.life.provider.dao.ArticleInfoDao;
import top.imuster.life.provider.service.ArticleCollectionService;
import top.imuster.life.provider.service.ArticleInfoService;
import top.imuster.life.provider.service.ArticleTagRelService;
import top.imuster.life.provider.service.ArticleTagService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * ArticleInfoService 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Service("articleInfoService")
@Slf4j
public class ArticleInfoServiceImpl extends BaseServiceImpl<ArticleInfo, Long> implements ArticleInfoService {

    @Value("${batch.size}")
    private int batchSize;   //批量处理浏览记录的大小

    @Resource
    private ArticleInfoDao articleInfoDao;

    @Resource
    ArticleTagService articleTagService;

    @Resource
    ArticleTagRelService articleTagRelService;

    @Resource
    private ArticleCollectionService articleCollectionService;

    @Override
    public BaseDao<ArticleInfo, Long> getDao() {
        return this.articleInfoDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void release(UserDto currentUser, ArticleInfo articleInfo) {
        articleInfo.setUserId(currentUser.getUserId());
        String tagIds = articleInfo.getTagIds();
        if (StringUtils.isNotBlank(tagIds)){
            String[] split = tagIds.split(",");
            ArrayList<ArticleTagInfo> articleTagInfos = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                long tagId = Long.parseLong(split[i]);
                articleTagInfos.add(new ArticleTagInfo(tagId));
            }
            articleTagService.insertEntry(articleTagInfos.toArray(new ArticleTagInfo[articleTagInfos.size()]));
        }
        articleInfoDao.insertEntry(articleInfo);
    }

    @Override
    public List<ArticleInfo> list(Page<ArticleInfo> page, Long userId) {
        ArticleInfo condition = page.getSearchCondition();
        if(condition == null){
            page.setSearchCondition(new ArticleInfo());
        }
        condition.setUserId(userId);
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        condition.setState(2);
        return articleInfoDao.selectListByCondition(page.getSearchCondition());
    }

    @Override
    public ArticleInfo getArticleDetailById(Long id) {
        ArticleInfo result = articleInfoDao.selectEntryList(id).get(0);

        ArticleTagRel condition = new ArticleTagRel();
        condition.setArticleId(id);
        List<ArticleTagRel> articleTagRels = articleTagRelService.selectEntryList(condition);

        //获得文章的标签
        if (result.getTagList() == null) result.setTagList(new ArrayList<>());
        articleTagRels.stream().forEach(articleTagRel -> {
            ArticleTagInfo articleTagInfo = articleTagService.selectEntryList(articleTagRel.getTagId()).get(0);
            result.getTagList().add(articleTagInfo);
        });
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
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "'article::brief::' + #p0")
    public ArticleInfo getBriefById(Long id) {
        ArticleInfo articleInfo = articleInfoDao.selectBriefById(id);
        List<String> tagNames = articleTagRelService.getArticleTagsById(id);
        articleInfo.setTagNames(tagNames);
        return articleInfo;
    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "#p0+'::top::five'")
    public List<ArticleInfo> hotTopicListByCategory(Long id) {
        return articleInfoDao.selectUpTop5ByCategoryId(id);
    }

    @Override
    public List<ArticleInfo> selectInfoByTargetIds(Long[] longs) {
        return articleInfoDao.selectInfoByTargetIds(longs);
    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_HOT_TOPIC_CACHE_KEY, key = "#p0")
    public ForumHotTopicInfo getBriefByHotTopicId(Long aLong) {
        return articleInfoDao.selectBriefByHotTopicId(aLong);
    }

    @Override
    public UserBriefDto getUserBriefByUserId(Long userId) {
        Long collectTotal = articleCollectionService.getCollectTotalByUserId(userId);
        UserBriefDto userBriefDto = articleInfoDao.selectUserBriefTotalById(userId);
        if(userBriefDto == null) userBriefDto = new UserBriefDto();
        userBriefDto.setCollectTotal(collectTotal);
        return userBriefDto;
    }

    @Override
    public void updateBrowserTimesFromRedis2Redis(List<BrowserTimesDto> res) {
        if(res == null || res.isEmpty()) return;
        HashSet<Long> targetIds = new HashSet<>();
        HashSet<Long> times = new HashSet<>();
        res.stream().forEach(browserTimesDto -> {
            Long targetId = browserTimesDto.getTargetId();
            Long total = browserTimesDto.getTotal();
            targetIds.add(targetId);
            times.add(total);
        });
        Long[] ids = targetIds.toArray(new Long[targetIds.size()]);
        Long[] totals = times.toArray(new Long[times.size()]);
        for (int i = 0; i <= ids.length / batchSize; i++){
            ArrayList<ArticleInfo> update = new ArrayList<>();
            Long[] selectIds = new Long[batchSize];
            for (int j = i * batchSize, x = 0; j < (i + 1) * batchSize; j++, x++) {
                selectIds[x] = ids[j];
                if(j == ids.length - 1) break;
            }
            Map<Long, Long> result = articleInfoDao.selectBrowserTimesByIds(ids);
            for (int z = 0; z < selectIds.length; z++){
                if(selectIds[z] == null || selectIds[z] == 0) break;
                Long selectId = selectIds[z];
                Long total = totals[i * batchSize + z];
                Long original = result.get(selectId);
                original = original + total;
                ArticleInfo condition = new ArticleInfo();
                condition.setId(selectId);
                condition.setBrowserTimes(original);
                update.add(condition);
            }
            articleInfoDao.updateBrowserTimesByCondition(update);
        }
    }

    @Override
    public void updateForwardTimesFromRedis2DB(List<ForwardDto> res) {
        articleInfoDao.updateForwardTimesByCondition(res);
    }

    @Override
    public Message<List<ArticleInfo>> getBriefByCategoryId(Long categoryId, Long pageSize, Long currentPage) {
        //根据分类获得该分类下的标签
        List<Long> tagIds = articleTagService.getTagByCategoryId(categoryId);

        //根据标签分页查询文章id
        List<Long> articleIds =  articleTagRelService.selectArticleIdsByIds(tagIds, pageSize, currentPage);

        //根据文章id获得文章的简略信息
        List<ArticleInfo> articleInfoList = articleInfoDao.selectArticleBriefByTagIds(articleIds);
        return Message.createBySuccess(articleInfoList);
    }
}