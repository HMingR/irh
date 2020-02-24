package top.imuster.user.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleTag;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.service.ForumServiceFeignApi;

import javax.annotation.Resource;

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

    @ApiOperation(value = "管理员分页查看文章信息，没有文章内容", httpMethod = "POST")
    @PostMapping("/article")
    public Message<Page<ArticleInfo>> articleList(@RequestBody Page<ArticleInfo> page){
        Page<ArticleInfo> articleInfoPage = null;
        try{
            articleInfoPage = forumServiceFeignApi.adminGetArticleList(page);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Message.createBySuccess(articleInfoPage);
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

    /**
     * @Author hmr
     * @Description 分页条件查询分类
     * @Date: 2020/2/6 19:30
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.forum.api.pojo.ArticleCategory>>
     **/
    @ApiOperation(value = "分页条件查询分类", httpMethod = "POST")
    @PostMapping("/category/list")
    public Message<Page<ArticleTag>> categoryList(@RequestBody Page<ArticleTag> page){
        return forumServiceFeignApi.adminCategoryList(page);
    }

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
    public Message<ArticleTag> getCategory(@PathVariable("id") Long id){
        ArticleTag category = forumServiceFeignApi.getCategoryInfoById(id);
        return Message.createBySuccess(category);
    }

    /**
     * @Author hmr
     * @Description 修改帖子分类信息
     * @Date: 2020/2/1 14:43
     * @param articleTag
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("修改帖子分类信息")
    @PutMapping("/category")
    public Message<String> editCategory(@RequestBody ArticleTag articleTag){
        boolean b = forumServiceFeignApi.editArticleCategory(articleTag);
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
    public Message<String> addCategory(@RequestBody ArticleTag category){
        boolean b = forumServiceFeignApi.addArticleCategory(category);
        if(b){
            return Message.createBySuccess();
        }
        return Message.createByError("修改失败,请刷新后重试或联系管理员");
    }


}
