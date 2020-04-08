package top.imuster.life.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.pojo.ForumHotTopicInfo;
import top.imuster.life.provider.dao.ForumHotTopicDao;
import top.imuster.life.provider.service.ArticleInfoService;
import top.imuster.life.provider.service.ForumHotTopicService;
import top.imuster.life.provider.service.RedisArticleAttitudeService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * ForumHotTopicService 实现类
 * @author 黄明人
 * @since 2020-02-13 21:12:30
 */
@Service("forumHotTopicService")
public class ForumHotTopicServiceImpl extends BaseServiceImpl<ForumHotTopicInfo, Long> implements ForumHotTopicService {
    @Resource
    private ForumHotTopicDao forumHotTopicDao;

    @Resource
    ArticleInfoService articleInfoService;

    @Resource
    RedisArticleAttitudeService redisArticleAttitudeService;

    @Override
    public BaseDao<ForumHotTopicInfo, Long> getDao() {
        return this.forumHotTopicDao;
    }

    @Override
    public void updateHotTopicFromRedis2DB(List<HashSet<Long>> res) {
        Long[] targetIds = res.get(0).toArray(new Long[res.get(0).size()]);
        Long[] scores = res.get(1).toArray(new Long[res.get(1).size()]);
        ForumHotTopicInfo condition = new ForumHotTopicInfo();
        for (int i = 0; i < targetIds.length; i++) {
            Long score = forumHotTopicDao.selectScoreByTargetId(targetIds[i]);
            condition.setTargetId(targetIds[i]);
            if(score == null || score == 0){
                condition.setScore(scores[i]);
                forumHotTopicDao.insertEntry(condition);
            }else{
                score = score + scores[i];
                condition.setScore(score);
                forumHotTopicDao.updateScoreByTargetId(condition);
            }
        }
    }

    @Override
    public Message<List<ForumHotTopicInfo>> totalHotTopicList(Long pageSize, Long currentPage) {
        HashMap<String, Long> params = new HashMap<>();
        params.put("startIndex", (currentPage - 1) * pageSize);
        params.put("pageSize", pageSize);
        List<ForumHotTopicInfo> list =forumHotTopicDao.selectMaxScoreTop(params);
        ArrayList<ForumHotTopicInfo> res = new ArrayList<>(list.size());
        if(list.isEmpty()){
            return Message.createBySuccess();
        }
        list.stream().forEach(forumHotTopic -> {
            Long targetId = forumHotTopic.getTargetId();
            if(targetId != null){
                ForumHotTopicInfo brief = articleInfoService.getBriefByHotTopicId(targetId);
                if(brief != null){
                    brief.setScore(forumHotTopic.getScore());
                    res.add(brief);
                }
            }
        });
        return Message.createBySuccess(res);
    }

    @Override
    public Message<List<ArticleInfo>> currentHotTopicList(int topic) {
        List<HashSet<Long>> res = redisArticleAttitudeService.getHotTopicFromRedis((long)topic);
        if(res == null || res.isEmpty()) {
            return null;
        }
        Long[] longs = res.get(0).toArray(new Long[res.get(0).size()]);
        final Long[] scores = res.get(1).toArray(new Long[res.get(1).size()]);
        HashMap<Long, Long> scoreMap = new HashMap<>();
        if(longs.length == 0 || scores.length == 0){
            //todo 当没有热搜的时候应该放历史热搜
            return Message.createBySuccess();
        }
        for (Long i : scores){
            scoreMap.put(i, i);
        }
        List<ArticleInfo> articleInfos = articleInfoService.selectInfoByTargetIds(longs);
        articleInfos.stream().forEach(articleInfo -> {
            Long aLong = scoreMap.get(articleInfo.getId());
            articleInfo.setScore(aLong);
        });
        return Message.createBySuccess(articleInfos);
    }


}