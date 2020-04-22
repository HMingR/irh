package top.imuster.life.provider.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.dto.UpCountDto;
import top.imuster.life.api.dto.UpDto;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.pojo.ArticleReviewInfo;
import top.imuster.life.api.pojo.UserForumAttributeInfo;
import top.imuster.life.provider.dao.UserForumAttributeDao;
import top.imuster.life.provider.service.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * UserForumAttributeService 实现类
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
@Service("userForumAttributeService")
public class UserForumAttributeServiceImpl extends BaseServiceImpl<UserForumAttributeInfo, Long> implements UserForumAttributeService {

    protected  final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserForumAttributeDao userForumAttributeDao;

    @Autowired
    RedisArticleAttitudeService redisArticleAttitudeService;

    @Resource
    ArticleReviewService articleReviewService;

    @Resource
    ArticleInfoService articleInfoService;

    @Resource
    ForumHotTopicService forumHotTopicService;


    @Override
    public BaseDao<UserForumAttributeInfo, Long> getDao() {
        return this.userForumAttributeDao;
    }

    @Override
    public void transUpFromRedis2DB() {
        List<UpDto> allUps = redisArticleAttitudeService.getAllUpFromRedis();
        if (allUps == null || allUps.isEmpty()) return;

        allUps.stream().forEach(upDto -> {
            Long targetId = upDto.getTargetId();
            Long userId = upDto.getUserId();
            UserForumAttributeInfo info = getInfoByTargetIdAndUserId(targetId, userId);
            if(info == null){
                //如果没有记录，则标识是第一次点赞，插入记录
                save2Db(upDto);
            }else{
                //如果有，则更新为相反的状态
                info.setState(upDto.getState());
                userForumAttributeDao.updateByKey(info);
            }
        });
    }

    @Override
    public void transHotTopicFromRedis2DB(Long topic) {
        List<HashSet<Long>> res = redisArticleAttitudeService.getHotTopicFromRedis(topic);
        if(res == null || res.isEmpty()) return;
        forumHotTopicService.updateHotTopicFromRedis2DB(res);
    }

    @Override
    public void transUpCountFromRedis2DB() {
        List<UpCountDto> list = redisArticleAttitudeService.getAllUpCountFromRedis();
        if(list == null || list.isEmpty()) return;

        Map<Integer, List<UpCountDto>> collect = list.stream().collect(Collectors.groupingBy(UpCountDto::getType));

        //文章
        List<UpCountDto> article = collect.get(1);

        List<ArticleInfo> articleInfos = null;
        //获得所有的id
        if(article != null && !article.isEmpty()){
            Object[] objects = article.stream().map(UpCountDto::getTargetId).toArray();
            Long[] articleIds = Arrays.stream(objects).map(p -> Long.valueOf(p.toString())).toArray(Long[]::new);
            articleInfos = articleInfoService.getUpTotalByIds(articleIds);
        }

        // 评论
        List<UpCountDto> review = collect.get(2);
        List<ArticleReviewInfo> articleReviewInfos = null;
        if(review != null && !review.isEmpty()){
            Object[] objects = review.stream().map(UpCountDto::getTargetId).toArray();
            Long[] reviewIds = Arrays.stream(objects).map(p -> Long.valueOf(p.toString())).toArray(Long[]::new);
            articleReviewInfos = articleReviewService.getUpTotalByIds(reviewIds);
        }

        for (UpCountDto upCountDto : list){
            Long count = upCountDto.getCount();
            Long targetId = upCountDto.getTargetId();
            if(upCountDto.getType() == 1){
                ArticleInfo info = articleInfos.stream().filter(articleInfo -> articleInfo.getId().equals(targetId)).findFirst().orElse(null);
                info.setUpTotal(info.getUpTotal() + count);
                articleInfoService.updateByKey(info);
            }else {
                ArticleReviewInfo info = articleReviewInfos.stream().filter(articleReview -> articleReview.getId().equals(targetId)).findFirst().orElse(null);
                //todo 存在空指针异常
                info.setUpTotal(info.getUpTotal() + count);
                articleReviewService.updateByKey(info);
            }
        }

    }

    @Override
    @Cacheable(value = "UpTotal",key = "#p2")
    public Long getUpTotalByTypeAndId(Long id, Integer type, String upTotalKey) {
        if(type == 1){
            return articleInfoService.getUpTotal(id);
        }else {
            return articleReviewService.getUpTotal(id);
        }
    }

    @Override
    public Message<Page<UserForumAttributeInfo>> getUpList(Page<UserForumAttributeInfo> page, Long userId) {
        UserForumAttributeInfo condition = page.getSearchCondition();
        if(null == condition){
            condition = new UserForumAttributeInfo();
        }
        if(StringUtils.isBlank(condition.getOrderField())){
            condition.setOrderField("create_time");
            condition.setOrderFieldType("DESC");
        }
        List<UserForumAttributeInfo> res = userForumAttributeDao.selectUpListByCondition(condition);
        page.setData(res);
        return null;
    }

    @Override
    public Message<Integer> getStateByTargetId(Integer type, Long id, Long userId) {
        UserForumAttributeInfo condition = new UserForumAttributeInfo();
        condition.setTargetId(id);
        condition.setType(type);
        condition.setUserId(userId);
        List<UserForumAttributeInfo> userForumAttributeInfos = userForumAttributeDao.selectEntryList(condition);
        if(userForumAttributeInfos == null || userForumAttributeInfos.isEmpty() || userForumAttributeInfos.get(0).getState() == 1) return Message.createBySuccess(1);
        return Message.createBySuccess(2);
    }


    /**
     * @Author hmr
     * @Description 将redis中的信息保存到db
     * @Date: 2020/2/8 19:43
     * @param upDto
     * @reture: void
     **/
    private void save2Db(UpDto upDto){
        UserForumAttributeInfo condition = new UserForumAttributeInfo();
        condition.setTargetId(upDto.getTargetId());
        condition.setUserId(upDto.getUserId());
        condition.setType(upDto.getType());
        userForumAttributeDao.insertEntry(condition);
    }

    /**
     * @Author hmr
     * @Description 根据目标id和点赞人id查询点赞记录
     * @Date: 2020/2/8 19:42
     * @param targetId
     * @param userId
     * @reture: UserForumAttributeInfo
     **/
    private UserForumAttributeInfo getInfoByTargetIdAndUserId(Long targetId, Long userId){
        UserForumAttributeInfo condition = new UserForumAttributeInfo();
        condition.setUserId(userId);
        condition.setTargetId(targetId);
        return userForumAttributeDao.selectEntryList(condition).get(0);
    }
}