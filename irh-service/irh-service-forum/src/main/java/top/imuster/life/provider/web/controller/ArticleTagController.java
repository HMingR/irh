package top.imuster.life.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.life.api.pojo.ArticleTagInfo;
import top.imuster.life.provider.service.ArticleTagService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ArticleCategoryController
 * @Description: ArticleCategoryController
 * @author: hmr
 * @date: 2020/2/3 11:06
 */
@RestController
@RequestMapping("/forum/tag")
@Api(value = "ArticleCategoryController - 论坛分类控制器")
public class ArticleTagController extends BaseController {

    @Resource
    ArticleTagService articleTagService;

    /**
     * @Author hmr
     * @Description 提供给用户选择分类的
     * @Date: 2020/2/3 11:08
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<top.imuster.forum.api.pojo.ArticleCategory>>
     **/
    @ApiOperation(value = "根据分类id获得标签", httpMethod = "GET")
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "'forum:tag:byCategory:'+#p0")
    @GetMapping("/list/{id}")
    public Message<List<ArticleTagInfo>> getListById(@PathVariable("id") Long id){
        List<ArticleTagInfo> tag = articleTagService.getTagByCategoryId(id);
        return Message.createBySuccess(tag);
    }

    @ApiOperation(value = "获得所有的标签", httpMethod = "GET")
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "'forum:alltag'")
    @GetMapping
    public Message<List<ArticleTagInfo>> getAllTag(){
        ArticleTagInfo articleTagInfo = new ArticleTagInfo();
        articleTagInfo.setState(2);
        List<ArticleTagInfo> articleTagInfos = articleTagService.selectEntryList(articleTagInfo);
        return Message.createBySuccess(articleTagInfos);
    }

    @ApiOperation("根据id获得标签的名字")
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "'forum::tag::name::'+#p0")
    @GetMapping("/name/{id}")
    public Message<String> getTagNameById(@PathVariable("id") Long id){
        String name = articleTagService.selectEntryList(id).get(0).getName();
        return Message.createBySuccess(name);
    }
}
