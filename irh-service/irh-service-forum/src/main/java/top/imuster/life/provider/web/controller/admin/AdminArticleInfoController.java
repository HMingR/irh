package top.imuster.life.provider.web.controller.admin;

import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.life.api.pojo.ArticleCategoryInfo;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.pojo.ArticleReviewInfo;
import top.imuster.life.provider.service.ArticleCategoryService;
import top.imuster.life.provider.service.ArticleInfoService;
import top.imuster.life.provider.service.ArticleReviewService;

import javax.annotation.Resource;

/**
 * @ClassName: AdminArticleInfoController
 * @Description: AdminArticleInfoController
 * @author: hmr
 * @date: 2020/6/9 9:38
 */
@RestController
@RequestMapping("/admin")
public class AdminArticleInfoController extends BaseController {

    @Resource
    ArticleInfoService articleInfoService;

    @Resource
    ArticleCategoryService articleCategoryService;

    @Resource
    ArticleReviewService articleReviewService;

    @DeleteMapping("/category/{id}")
    public Message<String> adminDeleteArticleCategory(@PathVariable("id") Long id) {
        ArticleCategoryInfo condition = new ArticleCategoryInfo();
        condition.setId(id);
        condition.setState(1);
        articleCategoryService.updateByKey(condition);
        return Message.createBySuccess();
    }

    @PostMapping("/category/list")
    public Message<Page<ArticleCategoryInfo>> adminCategoryList(@RequestBody Page<ArticleCategoryInfo> page) {
        Page<ArticleCategoryInfo> articleTagPage = articleCategoryService.selectPage(page.getSearchCondition(), page);
        return Message.createBySuccess(articleTagPage);
    }

    @GetMapping("/category/{id}")
    public Message<ArticleCategoryInfo> getCategoryInfoById(@PathVariable("id") Long id) {
        ArticleCategoryInfo articleCategoryInfo = articleCategoryService.selectEntryList(id).get(0);
        return Message.createBySuccess(articleCategoryInfo);
    }

    @PostMapping("/category")
    public Message<String> addArticleCategory(@RequestBody ArticleCategoryInfo category) {
        articleCategoryService.insertEntry(category);
        return Message.createBySuccess();
    }

    @PutMapping("/category")
    public Message<String> editArticleCategory(@RequestBody ArticleCategoryInfo category) {
        articleCategoryService.updateByKey(category);
        return Message.createBySuccess();
    }

    @DeleteMapping("/review/{id}")
    public Message<String> adminDeleteArticleReview(@PathVariable("id") Long id) {
        ArticleReviewInfo articleReviewInfo = new ArticleReviewInfo();
        articleReviewInfo.setId(id);
        articleReviewInfo.setState(1);
        articleReviewService.updateByKey(articleReviewInfo);
        return Message.createBySuccess();
    }

    @PostMapping("/article/list")
    public Message<Page<ArticleInfo>> adminGetArticleList(@RequestBody Page<ArticleInfo> page) {
        Page<ArticleInfo> list = articleInfoService.list(page, null);
        return Message.createBySuccess(list);
    }

    @DeleteMapping("/article/{id}")
    public Message<String> adminDeleteArticle(@PathVariable("id") Long id) {
        return articleInfoService.adminDeleteArticle(id);
    }

    @GetMapping("/tag/list/{pageSize}/{currentPage}")
    public Message<Page<String>> tagList(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        //todo
        return null;
    }



}
