package top.imuster.life.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.life.api.pojo.ArticleReview;
import top.imuster.life.provider.service.ArticleReviewService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ArticleReviewController
 * @Description: ArticleReviewController
 * @author: hmr
 * @date: 2020/2/1 19:59
 */
@RestController
@RequestMapping("/forum/review")
@Api("论坛留言控制器")
public class ArticleReviewController extends BaseController {

    @Resource
    ArticleReviewService articleReviewService;

    /**
     * @Author hmr
     * @Description 根据一级留言id获得其对应的所有留言或回复
     * @Date: 2020/2/3 10:39
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<ArticleReview>>
     **/
    @ApiOperation(value = "根据一级留言id获得其对应的所有留言或回复", httpMethod = "POST")
    @PostMapping("/child")
    public Message<List<ArticleReview>> reviewDetails(@RequestBody Page<ArticleReview> page){
        if(page.getSearchCondition() == null){
            page.setSearchCondition(new ArticleReview());
        }
        Long userId = getCurrentUserIdFromCookie();
        List<ArticleReview> articleReviews = articleReviewService.reviewDetails(page, userId);
        return Message.createBySuccess(articleReviews);
    }


    /**
     * @Author hmr
     * @Description 用户写留言
     * @Date: 2020/2/3 10:49
     * @param articleReview
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "用户写留言", httpMethod = "POST")
    @NeedLogin
    @PostMapping("/write")
    public Message<String> writeReview(@Validated(ValidateGroup.addGroup.class) @RequestBody ArticleReview articleReview, BindingResult bindingResult){
        validData(bindingResult);
        Long userId = getCurrentUserIdFromCookie();
        articleReview.setUserId(userId);
        articleReviewService.insertEntry(articleReview);
        return Message.createBySuccess();
    }


    /**
     * @Author hmr
     * @Description 根据文章id分页查询一级留言
     * @Date: 2020/3/15 10:26
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.life.api.pojo.ArticleReview>>
     **/
    @ApiOperation("根据文章id分页查询一级留言")
    @PostMapping
    public Message<Page<ArticleReview>> getArticleReviewByPage(@RequestBody Page<ArticleReview> page){
        if(page.getSearchCondition() == null){
            page.setSearchCondition(new ArticleReview());
        }
        Long userId = getCurrentUserIdFromCookie(false);
        return articleReviewService.selectFirstClassReviewListByArticleId(page, userId);
    }


    /**
     * @Author hmr
     * @Description 用户根据评论id删除评论
     * @Date: 2020/2/3 10:52
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "用户根据评论id删除自己的评论", httpMethod = "DELETE")
    @NeedLogin
    @DeleteMapping("/{id}")
    public Message<String> deleteReview(@PathVariable("id") Long id){
        ArticleReview condition = new ArticleReview();
        condition.setId(id);
        condition.setState(1);
        condition.setUserId(getCurrentUserIdFromCookie());
        articleReviewService.updateByKey(condition);
        return Message.createBySuccess();
    }


    /**
     * @Author hmr
     * @Description 用户查看自己的留言记录
     * @Date: 2020/2/3 11:04
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<ArticleReview>>
     **/
    @ApiOperation(value = "用户查看自己的留言记录,按照时间降序排列", httpMethod = "GET")
    @NeedLogin
    @GetMapping("/list")
    public Message<List<ArticleReview>> list(){
        Long userId = getCurrentUserIdFromCookie();
        List<ArticleReview> list = articleReviewService.list(userId);
        return Message.createBySuccess(list);
    }

}
