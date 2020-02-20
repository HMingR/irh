package top.imuster.life.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.life.api.pojo.ArticleTagCategory;
import top.imuster.life.provider.dao.ArticleTagCategoryDao;
import top.imuster.life.provider.service.ArticleTagCategoryService;

import javax.annotation.Resource;

/**
 * ArticleTagCategoryService 实现类
 * @author 黄明人
 * @since 2020-02-13 17:26:48
 */
@Service("articleTagCategoryService")
public class ArticleTagCategoryServiceImpl extends BaseServiceImpl<ArticleTagCategory, Long> implements ArticleTagCategoryService {

    @Resource
    private ArticleTagCategoryDao articleTagCategoryDao;

    @Override
    public BaseDao<ArticleTagCategory, Long> getDao() {
        return this.articleTagCategoryDao;
    }
}