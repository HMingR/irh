package top.imuster.forum.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.forum.provider.service.RedisArticleAttitudeService;
import top.imuster.forum.provider.service.UserForumAttributeService;

import javax.annotation.Resource;

/**
 * @ClassName: ArticleAttitudeController
 * @Description: 用户对于论坛中信息的态度  up  down
 * @author: hmr
 * @date: 2020/2/8 15:11
 */
@RestController
@RequestMapping("/forum")
public class ArticleAttitudeController extends BaseController {

    @Resource
    RedisArticleAttitudeService redisArticleAttitudeService;

    @Resource
    UserForumAttributeService userForumAttributeService;

    /**
     * @Author hmr
     * @Description 点赞操作,id为点赞的对象，type为对象的类型(1-文章  2-评论)
     * @Date: 2020/2/9 10:46
     * @param id
     * @param type
     * @reture: void
     **/
    @ApiOperation("点赞操作,id为点赞的对象，type为对象的类型(1-文章  2-评论)")
    @GetMapping("/up/{id}/{type}")
    public void upByType(@PathVariable("id") Long id, @PathVariable("type") Integer type){
        redisArticleAttitudeService.saveUp2Redis(id, type, getCurrentUserIdFromCookie());
        redisArticleAttitudeService.incrementUpCount(id, type);
    }

    /**
     * @Author hmr
     * @Description 取消点赞
     * @Date: 2020/2/9 10:47
     * @param id
     * @param type
     * @reture: void
     **/
    @ApiOperation("取消点赞")
    @GetMapping("/cancel/{id}/{type}")
    public void cancelUpByType(@PathVariable("id") Long id, @PathVariable("type") Integer type){
        redisArticleAttitudeService.saveUp2Redis(id, type, getCurrentUserIdFromCookie());
        redisArticleAttitudeService.decrementUpCount(id, type);
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
}
