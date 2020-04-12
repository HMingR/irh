package top.imuster.life.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleCategoryInfo;
import top.imuster.life.provider.dao.ArticleTagDao;
import top.imuster.life.provider.service.ArticleTagService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ArticleCategoryService 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends BaseServiceImpl<ArticleCategoryInfo, Long> implements ArticleTagService {

    @Resource
    private ArticleTagDao articleTagDao;

    @Override
    public BaseDao<ArticleCategoryInfo, Long> getDao() {
        return this.articleTagDao;
    }

    @Override
    public List<Long> getTagByCategoryId(Long id) {
        ArticleCategoryInfo condition = new ArticleCategoryInfo();
        condition.setState(2);
        condition.setCategoryId(id);
        List<ArticleCategoryInfo> articleCategoryInfos = articleTagDao.selectEntryList(condition);
        ArrayList<Long> list = (ArrayList<Long>) articleCategoryInfos.stream().map(ArticleCategoryInfo::getId).collect(Collectors.toList());
        return list;
    }

    @Override
    public Message<List<ArticleCategoryInfo>> getTagByCategoryIds(String ids) {
        String[] sids = ids.split(",");
        List<String> categoryIds = Arrays.asList(sids);
        List<ArticleCategoryInfo> list = articleTagDao.selectTagInfoByCategoryIds(categoryIds);
        return Message.createBySuccess(list);
    }

}