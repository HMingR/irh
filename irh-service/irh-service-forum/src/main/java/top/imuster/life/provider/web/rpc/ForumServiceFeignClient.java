package top.imuster.life.provider.web.rpc;

import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.life.api.pojo.ArticleCategoryInfo;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.pojo.ArticleReviewInfo;
import top.imuster.life.api.service.ForumServiceFeignApi;
import top.imuster.life.provider.service.ArticleCategoryService;
import top.imuster.life.provider.service.ArticleInfoService;
import top.imuster.life.provider.service.ArticleReviewService;

import javax.annotation.Resource;

/**
 * @ClassName: ForumServiceFeignClient
 * @Description: ForumServiceFeignClient
 * @author: hmr
 * @date: 2020/2/1 12:14
 */
@RestController
@RequestMapping("/forum/feign")
public class ForumServiceFeignClient extends BaseController implements ForumServiceFeignApi {

    @Resource
    ArticleInfoService articleInfoService;

    @Resource
    ArticleCategoryService articleCategoryService;

    @Resource
    ArticleReviewService articleReviewService;

    @Override
    @DeleteMapping("{id}")
    public Message<String>  adminDeleteArticle(@PathVariable("id") Long id) {
        return articleInfoService.adminDeleteArticle(id);
    }

    @Override
    @DeleteMapping("/category/{id}")
    public boolean adminDeleteArticleCategory(@PathVariable("id") Long id) {
        ArticleCategoryInfo condition = new ArticleCategoryInfo();
        condition.setId(id);
        condition.setState(1);
        int i = articleCategoryService.updateByKey(condition);
        return i == 1;
    }

    @Override
    @PostMapping("/category/list")
    public Message<Page<ArticleCategoryInfo>> adminCategoryList(@RequestBody Page<ArticleCategoryInfo> page) {
        Page<ArticleCategoryInfo> articleTagPage = articleCategoryService.selectPage(page.getSearchCondition(), page);
        return Message.createBySuccess(articleTagPage);
    }

    @Override
    @GetMapping("/category/{id}")
    public ArticleCategoryInfo getCategoryInfoById(@PathVariable("id") Long id) {
        return articleCategoryService.selectEntryList(id).get(0);
    }

    @Override
    @PostMapping("/category")
    public boolean addArticleCategory(@RequestBody ArticleCategoryInfo category) {
        int i = articleCategoryService.insertEntry(category);
        return i == 1;
    }

    @Override
    @PutMapping("/category")
    public boolean editArticleCategory(@RequestBody ArticleCategoryInfo category) {
        int i = articleCategoryService.updateByKey(category);
        return i == 1;
    }

    @Override
    @DeleteMapping("/review/{id}")
    public boolean adminDeleteArticleReview(@PathVariable("id") Long id) {
        ArticleReviewInfo articleReviewInfo = new ArticleReviewInfo();
        articleReviewInfo.setId(id);
        articleReviewInfo.setState(1);
        articleReviewService.updateByKey(articleReviewInfo);
        return false;
    }

    @Override
    @PostMapping("/article/list")
    public Message<Page<ArticleInfo>> adminGetArticleList(@RequestBody Page<ArticleInfo> page) {
        ArticleInfo searchCondition = page.getSearchCondition();
        searchCondition.setState(2);
        searchCondition.setOrderField("create_time");
        searchCondition.setOrderFieldType("DESC");
        page = articleInfoService.list(page, getCurrentUserIdFromCookie());
        return Message.createBySuccess(page);
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

    @Override
    public ArticleInfo getArticleInfoById(Long targetId) {
        return articleInfoService.getBriefById(targetId);
    }

}
