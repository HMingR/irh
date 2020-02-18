package top.imuster.life.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.forum.api.pojo.ArticleTag;
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
    @GetMapping("/list/{id}")
    public Message<List<ArticleTag>> getListById(@PathVariable("id") Long id){
        List<ArticleTag> tag = articleTagService.getTagByCategoryId(id);
        return Message.createBySuccess(tag);
    }

    @ApiOperation(value = "获得所有的标签", httpMethod = "GET")
    @GetMapping
    public Message<List<ArticleTag>> getAllTag(){
        ArticleTag articleTag = new ArticleTag();
        articleTag.setState(2);
        List<ArticleTag> articleTags = articleTagService.selectEntryList(articleTag);
        return Message.createBySuccess(articleTags);
    }
}
