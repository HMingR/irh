package top.imuster.life.provider.web.rpc;

import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleTag;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.pojo.ArticleReview;
import top.imuster.life.api.service.ForumServiceFeignApi;
import top.imuster.life.provider.service.ArticleTagService;
import top.imuster.life.provider.service.ArticleInfoService;
import top.imuster.life.provider.service.ArticleReviewService;

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
    ArticleTagService articleTagService;

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
        ArticleTag condition = new ArticleTag();
        condition.setId(id);
        condition.setState(1);
        int i = articleTagService.updateByKey(condition);
        return i == 1;
    }

    @Override
    @PostMapping("/category/list")
    public Message<Page<ArticleTag>> adminCategoryList(@RequestBody Page<ArticleTag> page) {
        Page<ArticleTag> articleTagPage = articleTagService.selectPage(page.getSearchCondition(), page);
        return Message.createBySuccess(articleTagPage);
    }

    @Override
    @GetMapping("/category/{id}")
    public ArticleTag getCategoryInfoById(@PathVariable("id") Long id) {
        return articleTagService.selectEntryList(id).get(0);
    }

    @Override
    @PostMapping("/category")
    public boolean addArticleCategory(@RequestBody ArticleTag category) {
        int i = articleTagService.insertEntry(category);
        return i == 1;
    }

    @Override
    @PutMapping("/category")
    public boolean editArticleCategory(@RequestBody ArticleTag category) {
        int i = articleTagService.updateByKey(category);
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
    public Page<ArticleInfo> adminGetArticleList(@RequestBody Page<ArticleInfo> page) {
        ArticleInfo searchCondition = page.getSearchCondition();
        searchCondition.setState(2);
        searchCondition.setOrderField("create_time");
        searchCondition.setOrderFieldType("DESC");
        List<ArticleInfo> list = articleInfoService.list(page);
        page.setData(list);
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
