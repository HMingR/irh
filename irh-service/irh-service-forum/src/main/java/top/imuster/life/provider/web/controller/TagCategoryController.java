package top.imuster.life.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleTagCategoryInfo;
import top.imuster.life.provider.service.ArticleTagCategoryService;

import javax.annotation.Resource;
import java.util.List;

@Api
@RestController
@RequestMapping("/forum/category")
public class TagCategoryController {

    @Resource
    ArticleTagCategoryService articleTagCategoryService;

    /**
     * 获得标签的分类列表
     * @return
     */
    @ApiOperation("获得标签的分类列表")
    @GetMapping
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "'irh::forum::tag::category::list'")
    public Message<List<ArticleTagCategoryInfo>> list(){
        ArticleTagCategoryInfo condition = new ArticleTagCategoryInfo();
        condition.setState(2);
        List<ArticleTagCategoryInfo> res = articleTagCategoryService.selectEntryList(condition);
        return Message.createBySuccess(res);
    }
}
