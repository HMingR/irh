package top.imuster.life.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.forum.api.pojo.ForumHotTopic;
import top.imuster.life.provider.dao.ForumHotTopicDao;
import top.imuster.life.provider.service.ForumHotTopicService;

import javax.annotation.Resource;

/**
 * ForumHotTopicService 实现类
 * @author 黄明人
 * @since 2020-02-13 21:12:30
 */
@Service("forumHotTopicService")
public class ForumHotTopicServiceImpl extends BaseServiceImpl<ForumHotTopic, Long> implements ForumHotTopicService {

    @Resource
    private ForumHotTopicDao forumHotTopicDao;

    @Override
    public BaseDao<ForumHotTopic, Long> getDao() {
        return this.forumHotTopicDao;
    }
}