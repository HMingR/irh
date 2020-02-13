package top.imuster.life.provider.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.core.dto.UserDto;
import top.imuster.forum.api.pojo.ArticleInfo;

import java.util.List;

/**
 * ArticleInfoService接口
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public interface ArticleInfoService extends BaseService<ArticleInfo, Long> {

    /**
     * @Author hmr
     * @Description 用户发布帖子
     * @Date: 2020/2/1 19:23
     * @param currentUser
     * @param articleInfo
     * @reture: void
     **/
    void release(UserDto currentUser, ArticleInfo articleInfo);

    /**
     * @Author hmr
     * @Description 用户查看自己发布的帖子，不包括帖子的内容
     * @Date: 2020/2/1 19:44
     * @param page
     * @reture: java.util.List<top.imuster.forum.api.pojo.ArticleInfo>
     **/
    List<ArticleInfo> list(Page<ArticleInfo> page);

    /**
     * @Author hmr
     * @Description 根据帖子id获得帖子的详细信息
     * @Date: 2020/2/2 10:57
     * @param id
     * @reture: top.imuster.forum.api.pojo.ArticleInfo
     **/
    ArticleInfo getArticleDetailById(Long id);

    /**
     * @Author hmr
     * @Description 根据帖子的id获得用户id
     * @Date: 2020/2/5 10:39
     * @param targetId
     * @reture: java.lang.Long
     **/
    Long getUserIdByArticleId(Long targetId);

    /**
     * @Author hmr
     * @Description 根据id数组查询点赞数和id
     * @Date: 2020/2/8 20:35
     * @param ids
     * @reture: java.util.List<top.imuster.forum.api.pojo.ArticleInfo>
     **/
    List<ArticleInfo> getUpTotalByIds(Long[] ids);

    /**
     * @Author hmr
     * @Description 根据id获得点赞总数
     * @Date: 2020/2/9 10:37
     * @param id
     * @reture: java.lang.Long
     **/
    Long getUpTotal(Long id);

    /**
     * @Author hmr
     * @Description 根据id获得帖子的简略信息，带缓存的
     * @Date: 2020/2/11 16:23
     * @param id
     * @reture: top.imuster.forum.api.pojo.ArticleInfo
     **/
    ArticleInfo getBriefById(Long id);

    /**
     *
     * @param id
     * @return
     */
    List<ArticleInfo> hotTopicListByCategory(Long id);
}