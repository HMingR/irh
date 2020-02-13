package top.imuster.life.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.forum.api.pojo.ArticleCategory;
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

    /**
     * @Author hmr
     * @Description 提供给用户选择分类的
     * @Date: 2020/2/3 11:08
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<top.imuster.forum.api.pojo.ArticleCategory>>
     **/
    @ApiOperation(value = "提供给用户选择分类的,已经生成了树形结构,前端只需要遍历数组", httpMethod = "GET")
    @GetMapping
    public Message<List<ArticleCategory>> getCategoryList(){
        List<ArticleCategory> categoryTree = articleCategoryService.getCategoryTree();
        return Message.createBySuccess(categoryTree);
    }
}
