package top.imuster.life.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.BrowserAnnotation;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.life.api.pojo.ArticleCollectionRel;
import top.imuster.life.provider.annotation.HotTopicAnnotation;
import top.imuster.life.provider.service.ArticleCollectionService;

import javax.annotation.Resource;

/**
 * @ClassName: ArticleCollectController
 * @Description: ArticleCollectController
 * @author: hmr
 * @date: 2020/2/9 10:57
 */
@RestController
@RequestMapping("/forum/collect")
@Api
public class ArticleCollectController extends BaseController {

    @Resource
    ArticleCollectionService articleCollectionService;

    /**
     * @Author hmr
     * @Description 用户收藏，type可取(1-取消收藏 2-收藏)
     * @Date: 2020/2/9 10:47
     * @param id
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("用户收藏")
    @HotTopicAnnotation(targetId = "#p0", score = 5)
    @BrowserAnnotation(value = "#p0", browserType = BrowserType.FORUM)
    @GetMapping("/{id}")
    public Message<String> collection(@PathVariable("id") Long id){
        Long userId = getCurrentUserIdFromCookie();
        return articleCollectionService.collect(userId, id);
    }

    @ApiOperation("取消收藏")
    @DeleteMapping("/{id}")
    public Message<String> unCollect(@PathVariable("id") Long id){
        return articleCollectionService.unCollect(id);
    }

    @ApiOperation(("用户分页查看自己的收藏列表，不需要条件,按照时间降序排列"))
    @PostMapping
    public Message<Page<ArticleCollectionRel>> collectList(Page<ArticleCollectionRel> page){
        return articleCollectionService.collectList(page, getCurrentUserIdFromCookie());
    }

    @ApiOperation("查看当前用户是否收藏该文章")
    @GetMapping("/state/{id}")
    public Message<Integer> collectState(@PathVariable("id") Long id){
        return articleCollectionService.getCollectStateByTargetId(id, getCurrentUserIdFromCookie(false));
    }
}
