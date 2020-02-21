package top.imuster.life.provider.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleForwardInfo;
import top.imuster.life.provider.dao.ArticleForwardInfoDao;
import top.imuster.life.provider.service.ArticleForwardInfoService;

import javax.annotation.Resource;

/**
 * ArticleForwardInfoService 实现类
 * @author 黄明人
 * @since 2020-02-21 17:23:45
 */
@Service("articleForwardInfoService")
public class ArticleForwardInfoServiceImpl extends BaseServiceImpl<ArticleForwardInfo, Long> implements ArticleForwardInfoService {

    @Resource
    private ArticleForwardInfoDao articleForwardInfoDao;

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
        return Message.createBySuccess("转发成功");
    }
}