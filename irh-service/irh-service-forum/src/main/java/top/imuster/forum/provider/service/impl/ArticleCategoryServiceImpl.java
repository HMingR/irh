package top.imuster.forum.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.forum.api.pojo.ArticleCategory;
import top.imuster.forum.provider.dao.ArticleCategoryDao;
import top.imuster.forum.provider.service.ArticleCategoryService;

import javax.annotation.Resource;

/**
 * ArticleCategoryService 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Service("articleCategoryService")
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategory, Long> implements ArticleCategoryService {

    @Resource
    private ArticleCategoryDao articleCategoryDao;

    @Override
    public BaseDao<ArticleCategory, Long> getDao() {
        return this.articleCategoryDao;
    }
}