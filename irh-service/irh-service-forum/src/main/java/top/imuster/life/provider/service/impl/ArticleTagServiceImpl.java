package top.imuster.life.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.life.api.pojo.ArticleTag;
import top.imuster.life.provider.dao.ArticleTagDao;
import top.imuster.life.provider.service.ArticleTagService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ArticleCategoryService 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends BaseServiceImpl<ArticleTag, Long> implements ArticleTagService {

    @Resource
    private ArticleTagDao articleTagDao;

    @Override
    public BaseDao<ArticleTag, Long> getDao() {
        return this.articleTagDao;
    }

    @Override
    public List<ArticleTag> getTagByCategoryId(Long id) {
        ArticleTag condition = new ArticleTag();
        condition.setState(2);
        condition.setCategoryId(id);
        List<ArticleTag> articleTags = articleTagDao.selectEntryList(condition);
        return articleTags;
    }
}