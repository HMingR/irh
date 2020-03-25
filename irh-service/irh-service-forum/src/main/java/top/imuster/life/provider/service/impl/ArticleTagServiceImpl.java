package top.imuster.life.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.life.api.pojo.ArticleTagInfo;
import top.imuster.life.provider.dao.ArticleTagDao;
import top.imuster.life.provider.service.ArticleTagService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ArticleCategoryService 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends BaseServiceImpl<ArticleTagInfo, Long> implements ArticleTagService {

    @Resource
    private ArticleTagDao articleTagDao;

    @Override
    public BaseDao<ArticleTagInfo, Long> getDao() {
        return this.articleTagDao;
    }

    @Override
    public List<Long> getTagByCategoryId(Long id) {
        ArticleTagInfo condition = new ArticleTagInfo();
        condition.setState(2);
        condition.setCategoryId(id);
        List<ArticleTagInfo> articleTagInfos = articleTagDao.selectEntryList(condition);
        ArrayList<Long> list = (ArrayList<Long>) articleTagInfos.stream().map(ArticleTagInfo::getId).collect(Collectors.toList());
        return list;
    }

}