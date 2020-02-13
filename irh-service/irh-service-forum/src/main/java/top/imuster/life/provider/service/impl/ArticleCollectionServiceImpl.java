package top.imuster.life.provider.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.forum.api.pojo.ArticleCollection;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.life.provider.dao.ArticleCollectionDao;
import top.imuster.life.provider.service.ArticleCollectionService;
import top.imuster.life.provider.service.ArticleInfoService;
import top.imuster.life.provider.service.RedisArticleAttitudeService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ArticleCollectionService 实现类
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
@Service("articleCollectionService")
@Slf4j
public class ArticleCollectionServiceImpl extends BaseServiceImpl<ArticleCollection, Long> implements ArticleCollectionService {

    @Resource
    private ArticleCollectionDao articleCollectionDao;

    @Resource
    private ArticleInfoService articleInfoService;

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    RedisArticleAttitudeService redisArticleAttitudeService;

    @Override
    public BaseDao<ArticleCollection, Long> getDao() {
        return this.articleCollectionDao;
    }

    @Override
    public Message<String> collect(Long userId, Long id) {
        ArticleCollection condition = new ArticleCollection();
        condition.setUserId(userId);
        condition.setArticleId(id);
        int i = articleCollectionDao.insertEntry(condition);
        if(i == 1){
            redisTemplate.opsForHash().increment(GlobalConstant.IRH_ARTICLE_COLLECT_MAP, id, 1);
            return Message.createBySuccess();
        }
        log.error("在向db存储用户收藏的信息时，返回值为0,插入的信息为{}", condition);
        return Message.createByError("系统繁忙,收藏失败,请稍后重试");
    }

    @Override
    public Message<String> unCollect(Long id) {
        ArticleCollection condition = new ArticleCollection();
        condition.setId(id);
        condition.setState(1);
        int i = articleCollectionDao.updateByKey(condition);
        if(i == 1){
            redisTemplate.opsForHash().increment(GlobalConstant.IRH_ARTICLE_COLLECT_MAP, id, -1);
            return Message.createBySuccess();
        }
        log.error("用户取消收藏失败,更新返回值为0,更新的主键id为{}", id);
        return Message.createByError("系统繁忙,取消收藏失败,请稍后重试");
    }

    @Override
    public void transCollectCountFromRedis2Db() {
        List<Map.Entry<Long, Long>> allCollect = redisArticleAttitudeService.getAllCollectCountFromRedis();
        Long[] ids = new Long[allCollect.size()];
        HashMap<Long, Long> map = new HashMap<>();
        int index = 0;
        for (Map.Entry<Long, Long> entry : allCollect) {
            Long key = entry.getKey();
            ids[index] = key;
            map.put(key, entry.getValue());
        }
        List<ArticleInfo> upTotalByIds = articleInfoService.getUpTotalByIds(ids);
        upTotalByIds.stream().forEach(articleInfo -> {
            Long id = articleInfo.getId();
            Long increTotal = map.get(id);
            articleInfo.setUpTotal(articleInfo.getUpTotal()+increTotal);
            articleInfoService.updateByKey(articleInfo);
        });
    }

    @Override
    public Message<Page<ArticleCollection>> collectList(Page<ArticleCollection> page, Long userId) {
        ArticleCollection searchCondition = page.getSearchCondition();
        if(null == searchCondition){
            searchCondition = new ArticleCollection();
        }
        //默认的排序顺序为按照时间降序排列
        if(StringUtils.isBlank(searchCondition.getOrderField())){
            searchCondition.setOrderField("create_time");
            searchCondition.setOrderFieldType("DESC");
        }
        searchCondition.setUserId(userId);
        List<ArticleCollection> res = articleCollectionDao.selectCollectByCondition(searchCondition);
        page.setResult(res);
        return Message.createBySuccess(page);
    }
}