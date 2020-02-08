package top.imuster.forum.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.forum.api.pojo.ArticleCollection;
import top.imuster.forum.provider.dao.ArticleCollectionDao;
import top.imuster.forum.provider.service.ArticleCollectionService;

import javax.annotation.Resource;

/**
 * ArticleCollectionService 实现类
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
@Service("articleCollectionService")
public class ArticleCollectionServiceImpl extends BaseServiceImpl<ArticleCollection, Long> implements ArticleCollectionService {

    @Resource
    private ArticleCollectionDao articleCollectionDao;

    @Override
    public BaseDao<ArticleCollection, Long> getDao() {
        return this.articleCollectionDao;
    }
}