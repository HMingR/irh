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
import top.imuster.life.api.pojo.ArticleCategoryInfo;
import top.imuster.life.provider.service.ArticleCategoryService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ArticleCategoryController
 * @Description: ArticleCategoryController
 * @author: hmr
 * @date: 2020/2/3 11:06
 */
@RestController
@RequestMapping("/forum/category")
@Api(value = "ArticleCategoryController - 论坛分类控制器")
public class ArticleCategoryController extends BaseController {

    @Resource
    ArticleCategoryService articleCategoryService;

    @ApiOperation(value = "获得所有的标签", httpMethod = "GET")
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "'forum:alltag'")
    @GetMapping
    public Message<List<ArticleCategoryInfo>> getAllTag(){
        ArticleCategoryInfo articleCategoryInfo = new ArticleCategoryInfo();
        articleCategoryInfo.setState(2);
        List<ArticleCategoryInfo> articleCategoryInfos = articleCategoryService.selectEntryList(articleCategoryInfo);
        return Message.createBySuccess(articleCategoryInfos);
    }

    @ApiOperation("根据id获得分类的名字")
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "'forum::tag::name::'+#p0")
    @GetMapping("/name/{id}")
    public Message<String> getTagNameById(@PathVariable("id") Long id){
        List<ArticleCategoryInfo> articleCategoryInfos = articleCategoryService.selectEntryList(id);
        if(articleCategoryInfos.isEmpty()){
            return Message.createBySuccess(String.valueOf(id));
        }
        return Message.createBySuccess(articleCategoryInfos.get(0).getName());
    }
}
