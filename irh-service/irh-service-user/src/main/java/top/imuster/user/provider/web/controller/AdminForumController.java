package top.imuster.user.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.forum.api.pojo.ArticleCategory;
import top.imuster.forum.api.service.ForumServiceFeignApi;

/**
 * @ClassName: AdminForumController
 * @Description: 后台管理论坛模块
 * @author: hmr
 * @date: 2020/1/31 20:54
 */
@RestController
@RequestMapping("/admin/forum")
@Api("后台管理论坛模块")
public class AdminForumController {

    @Autowired
    ForumServiceFeignApi forumServiceFeignApi;

    /**
     * @Author hmr
     * @Description 删除帖子分类
     * @Date: 2020/2/1 14:43
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("删除帖子分类")
    @DeleteMapping("/category/{id}")
    public Message<String> deleteCategory(@PathVariable("id") Long id){
        boolean b = forumServiceFeignApi.adminDeleteArticleCategory(id);
        if(b){
            return Message.createBySuccess();
        }
        return Message.createByError("删除失败,请刷新后重试或联系管理员");
    }

    /**
     * @Author hmr
     * @Description 根据id获得帖子分类信息
     * @Date: 2020/2/1 14:43
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.forum.api.pojo.ArticleCategory>
     **/
    @ApiOperation("根据id获得帖子分类信息")
    @GetMapping("/category/{id}")
    public Message<ArticleCategory> getCategory(@PathVariable("id") Long id){
        ArticleCategory category = forumServiceFeignApi.getCategoryInfoById(id);
        return Message.createBySuccess(category);
    }

    /**
     * @Author hmr
     * @Description 修改帖子分类信息
     * @Date: 2020/2/1 14:43
     * @param articleCategory
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("修改帖子分类信息")
    @PutMapping("/category")
    public Message<String> editCategory(@RequestBody ArticleCategory articleCategory){
        boolean b = forumServiceFeignApi.editArticleCategory(articleCategory);
        if(b){
            return Message.createBySuccess();
        }
        return Message.createByError("修改失败,请刷新后重试或联系管理员");
    }

    /**
     * @Author hmr
     * @Description 添加帖子分类
     * @Date: 2020/2/1 14:45
     * @param category
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("添加帖子分类")
    @PostMapping("/category")
    public Message<String> addCategory(@RequestBody ArticleCategory category){
        boolean b = forumServiceFeignApi.addArticleCategory(category);
        if(b){
            return Message.createBySuccess();
        }
        return Message.createByError("修改失败,请刷新后重试或联系管理员");
    }

    /**
     * @Author hmr
     * @Description 根据id删除帖子信息
     * @Date: 2020/2/1 14:44
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("根据id删除帖子信息")
    @DeleteMapping("/{id}")
    public Message<String> deleteArticleInfoById(@PathVariable("id") Long id){
        boolean b = forumServiceFeignApi.adminDeleteArticle(id);
        if(b) {
            return Message.createBySuccess();
        }
        return Message.createByError("删除失败,请刷新后重试或联系管理员");
    }
}
