package top.imuster.life.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.life.api.pojo.ArticleCategoryRel;
import top.imuster.life.provider.dao.ArticleCategoryRelDao;
import top.imuster.life.provider.service.ArticleCategoryRelService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ArticleCategoryRelService 实现类
 * @author 黄明人
 * @since 2020-02-16 16:19:34
 */
@Service("articleCategoryRelService")
public class ArticleCategoryRelServiceImpl extends BaseServiceImpl<ArticleCategoryRel, Long> implements ArticleCategoryRelService {

    @Resource
    private ArticleCategoryRelDao articleCategoryRelDao;

    @Override
    public BaseDao<ArticleCategoryRel, Long> getDao() {
        return this.articleCategoryRelDao;
    }

    @Override
    public List<String> getArticleTagsById(Long id) {
        List<String> names = articleCategoryRelDao.selectTagNameByArticleId(id);
        return names;
    }

    @Override
    public List<Long> selectArticleIdsByIds(List<Long> categoryIds, Long pageSize, Long currentPage) {
        Page<ArticleCategoryRel> page = new Page<>();
        page.setPageSize(pageSize.intValue());
        page.setCurrentPage(currentPage.intValue());
        ArticleCategoryRel articleCategoryRel = new ArticleCategoryRel();
        articleCategoryRel.setCategoryIds(categoryIds);
        articleCategoryRel.setStartIndex(page.getStartIndex());
        articleCategoryRel.setEndIndex(page.getEndIndex());
        return articleCategoryRelDao.selectArticleIdByPageAndTagIds(articleCategoryRel);
    }
}