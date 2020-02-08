package top.imuster.forum.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.forum.api.pojo.ArticleCollection;
import top.imuster.forum.api.pojo.UserForumAttribute;
import top.imuster.forum.provider.service.ArticleCollectionService;
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
    ArticleCollectionService articleCollectionService;

    @Resource
    RedisArticleAttitudeService redisArticleAttitudeService;

    @ApiOperation("点赞操作,id为点赞的对象，type为对象的类型(1-文章  2-评论)")
    @GetMapping("/{id}/{type}")
    public Message<String> upByType(@PathVariable("id") Long id, @PathVariable("type") Integer type){
        redisArticleAttitudeService.saveUp2Redis(id, type, getCurrentUserIdFromCookie());
        return Message.createBySuccess();
    }

    @ApiOperation("用户收藏")
    @GetMapping("/collection/{id}")
    public Message<String> collection(@PathVariable("id") Long id){
        Long userId = getCurrentUserIdFromCookie();
        ArticleCollection condition = new ArticleCollection();
        condition.setArticleId(id);
        condition.setUserId(userId);
        articleCollectionService.insertEntry(condition);
        return Message.createBySuccess();
    }
}
