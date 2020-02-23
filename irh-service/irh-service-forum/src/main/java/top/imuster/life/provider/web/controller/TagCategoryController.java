package top.imuster.life.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.life.api.pojo.ArticleTagCategory;
import top.imuster.life.provider.service.ArticleTagCategoryService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/forum/category")
public class TagCategoryController {

    @Autowired
    FileServiceFeignApi fileServiceFeignApi;

    @Resource
    ArticleTagCategoryService articleTagCategoryService;

    /**
     * 获得标签的分类列表
     * @return
     */
    @ApiOperation("获得标签的分类列表")
    @GetMapping
    public Message<List<ArticleTagCategory>> list(){
        ArticleTagCategory condition = new ArticleTagCategory();
        condition.setState(2);
        List<ArticleTagCategory> res = articleTagCategoryService.selectEntryList(condition);
        return Message.createBySuccess(res);
    }
}
