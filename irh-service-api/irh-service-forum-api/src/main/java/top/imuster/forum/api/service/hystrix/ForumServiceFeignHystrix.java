package top.imuster.forum.api.service.hystrix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.imuster.common.base.domain.Page;
import top.imuster.forum.api.pojo.ArticleTag;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.forum.api.service.ForumServiceFeignApi;

/**
 * @ClassName: ForumServiceFeignHystrix
 * @Description: ForumServiceFeignHystrix
 * @author: hmr
 * @date: 2020/2/1 12:10
 */
@Component
@Slf4j
public class ForumServiceFeignHystrix implements ForumServiceFeignApi {

    @Override
    public boolean adminDeleteArticle(Long id) {
        log.error("管理员根据id删除帖子失败,帖子id为{}", id);
        return false;
    }

    @Override
    public boolean adminDeleteArticleCategory(Long id) {
        log.error("管理员根据id删除帖子分类失败,帖子分类id为{}", id);
        return false;
    }

    @Override
    public Page<ArticleTag> adminCategoryList(Page<ArticleTag> page) {
        log.error("管理员分页条件查询帖子分类失败,page对象为{}",page);
        return null;
    }

    @Override
    public ArticleTag getCategoryInfoById(Long id) {
        log.error("管理员根据id获得帖子分类信息失败,帖子分类id为{}", id);
        return null;
    }

    @Override
    public boolean addArticleCategory(ArticleTag category) {
        log.error("管理员新增帖子分类失败,新增帖子信息为{}", category);
        return false;
    }

    @Override
    public boolean editArticleCategory(ArticleTag category) {
        log.error("管理员根据id提交修改帖子分类信息失败,帖子分类信息为{}", category);
        return false;
    }

    @Override
    public boolean adminDeleteArticleReview(Long id) {
        log.error("管理员根据留言id删除帖子留言失败,留言id为{}", id);
        return false;
    }

    @Override
    public Page<ArticleInfo> adminGetArticleList(Page<ArticleInfo> page) {
        log.error("管理员分页条件查询帖子信息失败,page对象为{}", page);
        return null;
    }

    @Override
    public Long getUserIdByType(Long targetId, Integer type) {
        log.error("管理员根据类型查看用户id失败");
        return null;
    }
}
