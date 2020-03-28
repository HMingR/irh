package top.imuster.life.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.life.api.pojo.UserForumAttributeInfo;
import top.imuster.life.provider.service.RedisArticleAttitudeService;
import top.imuster.life.provider.service.UserForumAttributeService;

import javax.annotation.Resource;

/**
 * @ClassName: ArticleAttitudeController
 * @Description: 用户对于论坛中信息的态度  点赞功能
 * @author: hmr
 * @date: 2020/2/8 15:11
 */
@RestController
@RequestMapping("/forum")
@Api
public class ArticleAttitudeController extends BaseController {

    @Resource
    RedisArticleAttitudeService redisArticleAttitudeService;

    @Resource
    UserForumAttributeService userForumAttributeService;

    /**
     * @Author hmr
     * @Description 给文章点赞
     * @Date: 2020/2/14 10:06
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    /*@ApiOperation("给文章点赞")
    @BrowserAnnotation(browserType = BrowserType.FORUM, disableBrowserTimes = true, value = "#p0")
    @GetMapping("/up/1/{id}")
    public Message<String> upArticleById(@PathVariable("id") Long id){
        redisArticleAttitudeService.saveUp2Redis(id, 1, getCurrentUserIdFromCookie());
        redisArticleAttitudeService.incrementUpCount(id, 1);
        return Message.createBySuccess();
    }*/

    /**
     * @Author hmr
     * @Description 给文章的评论点赞
     * @Date: 2020/2/14 10:06
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    /*@ApiOperation("给文章的评论点赞")
    @NeedLogin
    @GetMapping("/up/2/{id}")
    public Message<String> upReviewById(@PathVariable("id") Long id){
        redisArticleAttitudeService.saveUp2Redis(id, 2, getCurrentUserIdFromCookie());
        redisArticleAttitudeService.incrementUpCount(id, 2);
        return Message.createBySuccess();
    }*/


    /**
     * @Author hmr
     * @Description 在论坛模块中的点赞操作
     * @Date: 2020/3/14 10:48
     * @param type 1-文章   2-评论
     * @param targetId 点赞对象id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("给文章模块点赞  type取值 1-文章  2-评论")
    @GetMapping("/up/{type}/{id}")
    public Message<String> up(@PathVariable("type") Integer type, @PathVariable("id") Long targetId){
        redisArticleAttitudeService.saveUp2Redis(targetId, type, getCurrentUserIdFromCookie());
        redisArticleAttitudeService.incrementUpCount(targetId, type);
        return Message.createBySuccess();
    }


    /**
     * @Author hmr
     * @Description 取消点赞
     * @Date: 2020/2/9 10:47
     * @param id
     * @param type
     * @reture: void
     **/
    @ApiOperation("取消点赞")
    @NeedLogin
    @GetMapping("/cancel/{id}/{type}")
    public Message<String> cancelUpByType(@PathVariable("id") Long id, @PathVariable("type") Integer type){
        redisArticleAttitudeService.saveUp2Redis(id, type, getCurrentUserIdFromCookie());
        redisArticleAttitudeService.decrementUpCount(id, type);
        return Message.createBySuccess();
    }


    /**
     * @Author hmr
     * @Description TODO
     * @Date: 2020/3/24 10:12
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.life.api.pojo.UserForumAttributeInfo>>
     **/
    @ApiOperation("查看自己在文章模块点赞的记录")
    @PostMapping("/list")
    public Message<Page<UserForumAttributeInfo>> upList(@RequestBody Page<UserForumAttributeInfo> page){
        return userForumAttributeService.getUpList(page, getCurrentUserIdFromCookie());
    }


    /**
     * @Author hmr
     * @Description 根据id和type获得点赞总数
     * @Date: 2020/2/9 10:47
     * @param id
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.Long>
     **/
    @ApiOperation("根据id和type获得点赞总数")
    @GetMapping("/{id}/{type}")
    public Message<Long> getUpTotalByType(@PathVariable("id") Long id, @PathVariable("type") Integer type){
        Long total = userForumAttributeService.getUpTotalByTypeAndId(id, type, RedisUtil.getUpTotalKey(id, type));
        return Message.createBySuccess(total);
    }


    /**
     * @Author hmr
     * @Description 根据id查看是否点赞了该目标
     * @Date: 2020/2/24 12:03
     * @param type
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.Integer>
     **/
    @ApiOperation("根据id查看是否点赞了该目标1-未点赞  2-点赞")
    @GetMapping("/state/{type}/{id}")
    public Message<Integer> getUpStateByTargetId(@PathVariable("type") Integer type, @PathVariable("id") Long id){
        Long userId = getCurrentUserIdFromCookie(false);
        if(userId == null){
            return Message.createBySuccess(1);
        }
        return userForumAttributeService.getStateByTargetId(type, id, userId);
    }
}