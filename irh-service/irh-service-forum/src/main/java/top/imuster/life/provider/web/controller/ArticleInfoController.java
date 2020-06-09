package top.imuster.life.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.BrowserAnnotation;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.life.api.dto.UserBriefDto;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.provider.service.ArticleInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ForumController
 * @Description: ForumController
 * @author: hmr
 * @date: 2020/2/1 15:09
 */
@RestController
@RequestMapping("/forum/article")
@Api("论坛帖子控制器")
public class ArticleInfoController extends BaseController {

    @Resource
    ArticleInfoService articleInfoService;

    /**
     * @Author hmr
     * @Description 用户发布帖子
     * @Date: 2020/2/1 19:32
     * @param articleInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("用户发布帖子")
    @PostMapping
    public Message<String> releaseArticle(@RequestBody @Validated(ValidateGroup.addGroup.class) ArticleInfo articleInfo, BindingResult bindingResult) throws Exception {
        validData(bindingResult);
        Long userId = getCurrentUserIdFromCookie();
        articleInfoService.release(userId, articleInfo);
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 用户删除自己的帖子
     * @Date: 2020/2/1 19:34
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("用户删除自己的帖子")
    @NeedLogin
    @DeleteMapping("/{id}")
    public Message<String> deleteArticle(@PathVariable("id")Long id){
        //todo 需要校验
        ArticleInfo condition = new ArticleInfo();
        condition.setId(id);
        condition.setState(1);
        articleInfoService.updateByKey(condition);
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 用户查看自己发布的帖子,按照发布时间降序排列
     * @Date: 2020/2/1 19:40
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<ArticleInfo>>
     **/
    @ApiOperation(("用户条件查看自己发布的帖子(列表,没有帖子的内容),按照发布时间降序排列"))
    @PostMapping("/list")
    public Message<Page<ArticleInfo>> list(@RequestBody Page<ArticleInfo> page){
        page = articleInfoService.list(page, getCurrentUserIdFromCookie());
        return Message.createBySuccess(page);
    }

    /**
     * @Author hmr
     * @Description 根据用户id分页查看用户发布的文章
     * @Date: 2020/4/19 18:20
     * @param pageSize
     * @param currentPage
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.life.api.pojo.ArticleInfo>>
     **/
    @GetMapping("/user/{pageSize}/{currentPage}/{userId}")
    public Message<Page<ArticleInfo>> getListByUserId(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage")Integer currentPage, @PathVariable("userId") Long userId){
        Page<ArticleInfo> page = new Page<>();
        page.setPageSize(pageSize);
        page.setCurrentPage(currentPage);
        page =  articleInfoService.list(page, userId);
        return Message.createBySuccess(page);
    }

    /**
     * @Author hmr
     * @Description 根据id查看帖子的详细信息，
     * @Date: 2020/2/2 10:56
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<ArticleInfo>
     **/
    @ApiOperation("根据id查看帖子的所有信息")
    @GetMapping("/{id}")
    @BrowserAnnotation(browserType = BrowserType.FORUM, value = "#p0")
    public Message<ArticleInfo> getArticleInfoById(@PathVariable("id") Long id) {
        ArticleInfo articleDetail = articleInfoService.getArticleDetailById(id);
        return Message.createBySuccess(articleDetail);
    }

    /**
     * @Author hmr
     * @Description 根据id获得文章的简略信息
     * @Date: 2020/2/11 16:21
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<ArticleInfo>
     **/
    @ApiOperation("根据id获得文章的简略信息(文章标题,图片url,留言总数,点赞总数)")
    @GetMapping("/brief/{id}")
    public Message<ArticleInfo> getTitleAndUrl(@PathVariable("id") Long id){
        ArticleInfo res = articleInfoService.getBriefById(id);
        return Message.createBySuccess(res);
    }

    @ApiOperation("根据文章分类id获得点赞最多的5个帖子id,提供给主页的")
    @GetMapping("/hot/{id}")
    public Message<List<ArticleInfo>> hotTopicList(@PathVariable("id") Long id){
        List<ArticleInfo> list = articleInfoService.hotTopicListByCategory(id);
        return Message.createBySuccess(list);
    }

    @ApiOperation("获得用户的获赞总数、收藏文章总数、文章被浏览总数")
    @GetMapping("/user")
    public Message<UserBriefDto> getUserForumBrief(){
        Long userId = getCurrentUserIdFromCookie(false);
        if(userId == null){
            return Message.createBySuccess();
        }
        UserBriefDto userBriefDto = articleInfoService.getUserBriefByUserId(userId);
        List<Long> userArticleRank = articleInfoService.getUserArticleRank(100, 1);
        int i = userArticleRank.indexOf(userId) + 1;
        userBriefDto.setRanking(i == 0 ? "暂未上榜":String.valueOf(i));
        return Message.createBySuccess(userBriefDto);
    }

    @ApiOperation("根据传入的id获得用户获赞总数、收藏文章总数、文章被浏览总数")
    @GetMapping("/user/{id}")
    public Message<UserBriefDto> getUserForumBrief(@PathVariable("id") Long userId){
        UserBriefDto userBriefDto = articleInfoService.getUserBriefByUserId(userId);
        return Message.createBySuccess(userBriefDto);
    }

    @ApiOperation("获得论坛模块的用户排名")
    @GetMapping("/rank/{pageSize}/{currentPage}")
    public Message<List<Long>> getArticleRank(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        List<Long> userId = articleInfoService.getUserArticleRank(pageSize, currentPage);
        return Message.createBySuccess(userId);
    }

    @ApiOperation("根据文章分类id获得该分类下的文章,按照点赞和发布时间排序")
    @GetMapping("/brief/category/{pageSize}/{currentPage}/{categoryId}")
    public Message<List<ArticleInfo>> getArticleBriefByCategoryId(@PathVariable Long categoryId, @PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage ){
        return articleInfoService.getBriefByCategoryId(categoryId, pageSize, currentPage);
    }
}
