package top.imuster.life.api.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.service.hystrix.ForumServiceFeignHystrix;

/**
 * @ClassName: ForumServiceFeignApi
 * @Description: ForumServiceFeignApi
 * @author: hmr
 * @date: 2020/2/1 12:07
 */
@FeignClient(value = "forum-service", path = "/forum/feign", fallbackFactory = ForumServiceFeignHystrix.class)
public interface ForumServiceFeignApi {

    /**
     * @Author hmr
     * @Description 提供给管理员的删除帖子接口
     * @Date: 2020/2/1 12:11
     * @param id 帖子id
     * @reture: boolean
     **/
    @DeleteMapping("/{id}")
    Message<String> adminDeleteArticle(@PathVariable("id") Long id);

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
     * @Description 根据type获得用户的id
     * @Date: 2020/2/5 10:35
     * @param targetId 帖子id或者帖子留言的id
     * @param type 帖子或者留言 4-帖子 5-帖子留言
     * @reture: java.lang.Long
     **/
    @GetMapping("/consumer/{targetId}/{type}")
    Long getUserIdByType(@PathVariable("targetId") Long targetId, @PathVariable("type") Integer type);


    @GetMapping("/article/{targetId}")
    ArticleInfo getArticleInfoById(@PathVariable("targetId") Long targetId);
}
