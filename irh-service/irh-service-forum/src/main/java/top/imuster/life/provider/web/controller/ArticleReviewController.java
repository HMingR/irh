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
import top.imuster.life.api.pojo.ArticleReviewInfo;
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
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<ArticleReviewInfo>>
     **/
    @ApiOperation(value = "根据一级留言id获得其对应的所有留言或回复", httpMethod = "POST")
    @PostMapping("/child")
    public Message<List<ArticleReviewInfo>> reviewDetails(@RequestBody Page<ArticleReviewInfo> page){
        if(page.getSearchCondition() == null){
            page.setSearchCondition(new ArticleReviewInfo());
        }
        Long userId = getCurrentUserIdFromCookie();
        List<ArticleReviewInfo> articleReviewInfos = articleReviewService.reviewDetails(page, userId);
        return Message.createBySuccess(articleReviewInfos);
    }


    /**
     * @Author hmr
     * @Description 用户写留言
     * @Date: 2020/2/3 10:49
     * @param articleReviewInfo
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation(value = "用户写留言", httpMethod = "POST")
    @NeedLogin
    @PostMapping("/write")
    public Message<String> writeReview(@Validated(ValidateGroup.addGroup.class) @RequestBody ArticleReviewInfo articleReviewInfo, BindingResult bindingResult){
        validData(bindingResult);
        Long userId = getCurrentUserIdFromCookie();
        articleReviewInfo.setUserId(userId);
        articleReviewService.insertEntry(articleReviewInfo);
        return Message.createBySuccess();
    }


    /**
     * @Author hmr
     * @Description 根据文章id分页查询一级留言
     * @Date: 2020/3/15 10:26
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.life.api.pojo.ArticleReviewInfo>>
     **/
    @ApiOperation("根据文章id分页查询一级留言")
    @GetMapping("/firstClass/{pageSize}/{currentPage}/{articleId}")
    public Message<List<ArticleReviewInfo>> getArticleReviewByPage(@PathVariable("articleId") Long articleId, @PathVariable("currentPage") Integer currentPage, @PathVariable("pageSize") Integer pageSize){
        Long userId = getCurrentUserIdFromCookie(false);
        return articleReviewService.selectFirstClassReviewListByArticleId(articleId, currentPage, pageSize, userId);
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
        ArticleReviewInfo condition = new ArticleReviewInfo();
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
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<ArticleReviewInfo>>
     **/
    @ApiOperation(value = "用户查看自己的留言记录,按照时间降序排列", httpMethod = "GET")
    @NeedLogin
    @GetMapping("/list/{pageSize}/{currentPage}")
    public Message<Page<ArticleReviewInfo>> list(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        Long userId = getCurrentUserIdFromCookie();
        return articleReviewService.list(userId, pageSize, currentPage);
    }

}
