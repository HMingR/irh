package top.imuster.life.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.forum.api.pojo.ArticleCategory;
import top.imuster.life.provider.dao.ArticleCategoryDao;
import top.imuster.life.provider.service.ArticleCategoryService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<ArticleCategory> getCategoryTree() {
        ArticleCategory condition = new ArticleCategory();
        condition.setState(2);
        List<ArticleCategory> articleCategories = articleCategoryDao.selectEntryList(condition);

        List<ArticleCategory> parents = new ArrayList<>();
        articleCategories.stream().forEach(articleCategory ->{
            if(articleCategory.getParentId() == 0){
                parents.add(articleCategory);
            }
        });

        for (ArticleCategory parent : parents) {
            tree(articleCategories, parent);
        }
        return parents;
    }

    private void tree(List<ArticleCategory> articleCategories, ArticleCategory parent){
        for (ArticleCategory articleCategory : articleCategories) {
            if(articleCategory.getParentId() == parent.getId()){
                parent.getChilds().add(articleCategory);
                tree(articleCategories, articleCategory);
            }
        }

    }

}