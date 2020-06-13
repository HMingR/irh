package top.imuster.user.provider.web.controller;

import io.swagger.annotations.Api;

/**
 * @ClassName: AdminForumController
 * @Description: 后台管理论坛模块
 * @author: hmr
 * @date: 2020/1/31 20:54
 */
//@RestController
//@RequestMapping("/admin/forum")
@Api("后台管理论坛模块")
public class AdminForumController {
/*

    @Autowired
    ForumServiceFeignApi forumServiceFeignApi;

    @ApiOperation(value = "管理员分页查看文章信息，没有文章内容", httpMethod = "POST")
    @PostMapping("/article")
    public Message<Page<ArticleInfo>> articleList(@RequestBody Page<ArticleInfo> page){
        return forumServiceFeignApi.adminGetArticleList(page);
    }

    */
/**
     * @Author hmr
     * @Description 根据id删除帖子信息
     * @Date: 2020/2/1 14:44
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **//*

    @ApiOperation("根据id删除帖子信息")
    @DeleteMapping("/{id}")
    public Message<String> deleteArticleInfoById(@PathVariable("id") Long id){
        return forumServiceFeignApi.adminDeleteArticle(id);
    }

    */
/**
     * @Author hmr
     * @Description 分页条件查询分类
     * @Date: 2020/2/6 19:30
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.forum.api.pojo.ArticleCategory>>
     **//*

    @ApiOperation(value = "分页条件查询分类", httpMethod = "POST")
    @PostMapping("/category/list")
    public Message<Page<ArticleCategoryInfo>> categoryList(@RequestBody Page<ArticleCategoryInfo> page){
        return forumServiceFeignApi.adminCategoryList(page);
    }

    */
/**
     * @Author hmr
     * @Description 删除帖子分类
     * @Date: 2020/2/1 14:43
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **//*

    @ApiOperation("删除帖子分类")
    @DeleteMapping("/category/{id}")
    public Message<String> deleteCategory(@PathVariable("id") Long id){
        boolean b = forumServiceFeignApi.adminDeleteArticleCategory(id);
        if(b){
            return Message.createBySuccess();
        }
        return Message.createByError("删除失败,请刷新后重试或联系管理员");
    }

    */
/**
     * @Author hmr
     * @Description 根据id获得帖子分类信息
     * @Date: 2020/2/1 14:43
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.forum.api.pojo.ArticleCategory>
     **//*

    @ApiOperation("根据id获得帖子分类信息")
    @GetMapping("/category/{id}")
    public Message<ArticleCategoryInfo> getCategory(@PathVariable("id") Long id){
        ArticleCategoryInfo category = forumServiceFeignApi.getCategoryInfoById(id);
        return Message.createBySuccess(category);
    }

    */
/**
     * @Author hmr
     * @Description 修改帖子分类信息
     * @Date: 2020/2/1 14:43
     * @param articleCategoryInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **//*

    @ApiOperation("修改帖子分类信息")
    @PutMapping("/category")
    public Message<String> editCategory(@RequestBody ArticleCategoryInfo articleCategoryInfo){
        boolean b = forumServiceFeignApi.editArticleCategory(articleCategoryInfo);
        if(b){
            return Message.createBySuccess();
        }
        return Message.createByError("修改失败,请刷新后重试或联系管理员");
    }

    */
/**
     * @Author hmr
     * @Description 添加帖子分类
     * @Date: 2020/2/1 14:45
     * @param category
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **//*

    @ApiOperation("添加帖子分类")
    @PostMapping("/category")
    public Message<String> addCategory(@RequestBody ArticleCategoryInfo category){
        boolean b = forumServiceFeignApi.addArticleCategory(category);
        if(b){
            return Message.createBySuccess();
        }
        return Message.createByError("修改失败,请刷新后重试或联系管理员");
    }
*/


}
