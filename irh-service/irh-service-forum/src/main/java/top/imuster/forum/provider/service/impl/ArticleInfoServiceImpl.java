package top.imuster.forum.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.forum.provider.dao.ArticleInfoDao;
import top.imuster.forum.provider.service.ArticleInfoService;

import javax.annotation.Resource;

/**
 * ArticleInfoService 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Service("articleInfoService")
public class ArticleInfoServiceImpl extends BaseServiceImpl<ArticleInfo, Long> implements ArticleInfoService {

    @Resource
    private ArticleInfoDao articleInfoDao;

    @Override
    public BaseDao<ArticleInfo, Long> getDao() {
        return this.articleInfoDao;
    }
}