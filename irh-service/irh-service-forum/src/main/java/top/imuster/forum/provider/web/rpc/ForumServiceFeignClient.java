package top.imuster.forum.provider.web.rpc;

import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.forum.api.pojo.ArticleCategory;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.forum.api.pojo.ArticleReview;
import top.imuster.forum.api.service.ForumServiceFeignApi;
import top.imuster.forum.provider.service.ArticleCategoryService;
import top.imuster.forum.provider.service.ArticleInfoService;
import top.imuster.forum.provider.service.ArticleReviewService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ForumServiceFeignClient
 * @Description: ForumServiceFeignClient
 * @author: hmr
 * @date: 2020/2/1 12:14
 */
@RestController
@RequestMapping("/forum/feign")
public class ForumServiceFeignClient implements ForumServiceFeignApi {

    @Resource
    ArticleInfoService articleInfoService;

    @Resource
    ArticleCategoryService articleCategoryService;

    @Resource
    ArticleReviewService articleReviewService;

    @Override
    @DeleteMapping("{id}")
    public boolean adminDeleteArticle(@PathVariable("id") Long id) {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setId(id);
        int i = articleInfoService.deleteByCondtion(articleInfo);
        return i == 1;
    }

    @Override
    @DeleteMapping("/category/{id}")
    public boolean adminDeleteArticleCategory(@PathVariable("id") Long id) {
        ArticleCategory condition = new ArticleCategory();
        condition.setId(id);
        condition.setState(1);
        int i = articleCategoryService.updateByKey(condition);
        return i == 1;
    }

    @Override
    @PostMapping("/category/list")
    public Page<ArticleCategory> adminCategoryList(Page<ArticleCategory> page) {
        return articleCategoryService.selectPage(page.getSearchCondition(), page);
    }

    @Override
    @GetMapping("/category/{id}")
    public ArticleCategory getCategoryInfoById(@PathVariable("id") Long id) {
        return articleCategoryService.selectEntryList(id).get(0);
    }

    @Override
    @PostMapping("/category")
    public boolean addArticleCategory(@RequestBody ArticleCategory category) {
        int i = articleCategoryService.insertEntry(category);
        return i == 1;
    }

    @Override
    @PutMapping("/category")
    public boolean editArticleCategory(@RequestBody ArticleCategory category) {
        int i = articleCategoryService.updateByKey(category);
        return i == 1;
    }

    @Override
    @DeleteMapping("/review/{id}")
    public boolean adminDeleteArticleReview(@PathVariable("id") Long id) {
        ArticleReview articleReview = new ArticleReview();
        articleReview.setId(id);
        articleReview.setState(1);
        articleReviewService.updateByKey(articleReview);
        return false;
    }

    @Override
    @PostMapping("/article/list")
    public Page<ArticleInfo> adminGetArticleList(Page<ArticleInfo> page) {
        ArticleInfo searchCondition = page.getSearchCondition();
        searchCondition.setState(2);
        searchCondition.setOrderField("create_time");
        searchCondition.setOrderFieldType("DESC");
        List<ArticleInfo> list = articleInfoService.list(page);
        page.setResult(list);
        return page;
    }

    @Override
    @GetMapping("/consumer/{targetId}/{type}")
    public Long getUserIdByType(@PathVariable("targetId") Long targetId, @PathVariable("type") Integer type) {
        Long userId = null;
        if(type == 4){
            userId = articleInfoService.getUserIdByArticleId(targetId);
        }else if(type == 5){
            userId = articleReviewService.getUserIdByArticleReviewId(targetId);
        }
        return userId;
    }
}
