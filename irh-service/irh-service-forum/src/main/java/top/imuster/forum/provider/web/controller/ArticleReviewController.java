package top.imuster.forum.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.forum.api.pojo.ArticleReview;
import top.imuster.forum.provider.service.ArticleReviewService;

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
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<top.imuster.forum.api.pojo.ArticleReview>>
     **/
    @ApiOperation(value = "根据一级留言id获得其对应的所有留言或回复", httpMethod = "GET")
    @GetMapping("/{id}")
    public Message<List<ArticleReview>> reviewDetails(@PathVariable("id") Long id){
        List<ArticleReview> articleReviews = articleReviewService.reviewDetails(id);
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
    public Message writeReview(@Validated(ValidateGroup.addGroup.class) @RequestBody ArticleReview articleReview, BindingResult bindingResult){
        validData(bindingResult);
        Long userId = getCurrentUserIdFromCookie();
        articleReview.setUserId(userId);
        articleReviewService.insertEntry(articleReview);
        return Message.createBySuccess();
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
    public Message deleteReview(@PathVariable("id") Long id){
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
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<top.imuster.forum.api.pojo.ArticleReview>>
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
