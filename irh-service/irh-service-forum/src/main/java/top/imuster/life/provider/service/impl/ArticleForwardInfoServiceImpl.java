package top.imuster.life.provider.service.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.dto.ForwardDto;
import top.imuster.life.api.pojo.ArticleForwardInfo;
import top.imuster.life.provider.dao.ArticleForwardInfoDao;
import top.imuster.life.provider.service.ArticleForwardInfoService;
import top.imuster.life.provider.service.ArticleInfoService;
import top.imuster.life.provider.service.RedisArticleAttitudeService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ArticleForwardInfoService 实现类
 * @author 黄明人
 * @since 2020-02-21 17:23:45
 */
@Service("articleForwardInfoService")
public class ArticleForwardInfoServiceImpl extends BaseServiceImpl<ArticleForwardInfo, Long> implements ArticleForwardInfoService {

    @Resource
    private ArticleForwardInfoDao articleForwardInfoDao;

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    RedisArticleAttitudeService redisArticleAttitudeService;

    @Resource
    ArticleInfoService articleInfoService;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public BaseDao<ArticleForwardInfo, Long> getDao() {
        return this.articleForwardInfoDao;
    }

    @Override
    public Message<Page<ArticleForwardInfo>> getPageByUserId(Long currentUserIdFromCookie, Integer currentPage) {
        Page<ArticleForwardInfo> page = new Page<>();
        page.setCurrentPage(currentPage);
        ArticleForwardInfo condition = new ArticleForwardInfo();
        condition.setState(2);
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        page.setSearchCondition(condition);
        Page<ArticleForwardInfo> res = this.selectPage(condition, page);
        return Message.createBySuccess(res);
    }

    @Override
    public Message<String> forward(ArticleForwardInfo articleForwardInfo) {
        articleForwardInfoDao.insertEntry(articleForwardInfo);
        redisTemplate.opsForHash().increment(GlobalConstant.IRH_FORUM_FORWARD_TIMES_MAP, articleForwardInfo.getId(), 1);
        return Message.createBySuccess("转发成功");
    }

    @Override
    public void transForwardTimesFromRedis2DB() {
        List<ForwardDto> res = redisArticleAttitudeService.getAllForwardCountFromRedis();
        if(res == null || res.isEmpty()) return;
        articleInfoService.updateForwardTimesFromRedis2DB(res);
    }
}