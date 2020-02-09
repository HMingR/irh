package top.imuster.forum.api.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.forum.api.pojo.ArticleCategory;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.forum.api.service.hystrix.ForumServiceFeignHystrix;

/**
 * @ClassName: ForumServiceFeignApi
 * @Description: ForumServiceFeignApi
 * @author: hmr
 * @date: 2020/2/1 12:07
 */
@FeignClient(value = "forum-service", path = "/forum/feign", fallback = ForumServiceFeignHystrix.class, fallbackFactory = Exception.class)
public interface ForumServiceFeignApi {

    /**
     * @Author hmr
     * @Description 提供给管理员的删除帖子接口
     * @Date: 2020/2/1 12:11
     * @param id 帖子id
     * @reture: boolean
     **/
    @DeleteMapping("/{id}")
    boolean adminDeleteArticle(@PathVariable("id") Long id);

    /**
     * @Author hmr
     * @Description 根据id删除论坛分类
     * @Date: 2020/2/1 12:18
     * @param id
     * @reture: boolean
     **/
    @DeleteMapping("/category/{id}")
    boolean adminDeleteArticleCategory(@PathVariable("id") Long id);

    @PostMapping("/category/list")
    Page<ArticleCategory> adminCategoryList(@RequestBody Page<ArticleCategory> page);

    /**
     * @Author hmr
     * @Description 根据id获得分类信息
     * @Date: 2020/2/1 12:23
     * @param id
     * @reture: top.imuster.forum.api.pojo.ArticleCategory
     **/
    @GetMapping("/category/{id}")
    ArticleCategory getCategoryInfoById(@PathVariable("id") Long id);

    /**
     * @Author hmr
     * @Description 添加分类
     * @Date: 2020/2/1 12:21
     * @param category
     * @reture: boolean
     **/
    @PutMapping("/category")
    boolean addArticleCategory(@RequestBody ArticleCategory category);

    /**
     * @Author hmr
     * @Description 修改分类信息
     * @Date: 2020/2/1 12:23
     * @param category
     * @reture: boolean
     **/
    @PutMapping("/category")
    boolean editArticleCategory(@RequestBody ArticleCategory category);

    /**
     * @Author hmr
     * @Description 根据留言id删除帖子留言
     * @Date: 2020/2/1 12:20
     * @param id
     * @reture: boolean
     **/
    @DeleteMapping("/review/{id}")
    boolean adminDeleteArticleReview(@PathVariable("id") Long id);

    /**
     * @Author hmr
     * @Description 管理员分页查看帖子信息,不显示内容
     * @Date: 2020/2/6 15:05
     * @param page
     * @reture: top.imuster.common.base.domain.Page<top.imuster.forum.api.pojo.ArticleInfo>
     **/
    @PostMapping("/article/list")
    Page<ArticleInfo> adminGetArticleList(@RequestBody Page<ArticleInfo> page);

    /**
     * @Author hmr
     * @Description 根据type获得用户的id
     * @Date: 2020/2/5 10:35
     * @param targetId 帖子id或者帖子留言的id
     * @param type 帖子或者留言 4-帖子 5-帖子留言
     * @reture: java.lang.Long
     **/
    @GetMapping("/consumer/{targetId}/{type}")
    Long getUserIdByType(@PathVariable("targetId") Long targetId, @PathVariable("type") Integer type);
}
