package top.imuster.forum.provider.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.dto.UserDto;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.forum.provider.dao.ArticleInfoDao;
import top.imuster.forum.provider.exception.ForumException;
import top.imuster.forum.provider.service.ArticleInfoService;

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
}