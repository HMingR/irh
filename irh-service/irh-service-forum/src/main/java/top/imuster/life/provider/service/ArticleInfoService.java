package top.imuster.life.provider.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.life.api.dto.ForwardDto;
import top.imuster.life.api.dto.UserBriefDto;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.pojo.ForumHotTopicInfo;

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
    void release(Long userId, ArticleInfo articleInfo) throws Exception;

    /**
     * @Author hmr
     * @Description 用户查看自己发布的帖子，不包括帖子的内容
     * @Date: 2020/2/1 19:44
     * @param page
     * @param userId
     * @reture: java.util.List<ArticleInfo>
     **/
    Page<ArticleInfo> list(Page<ArticleInfo> page, Long userId);

    /**
     * @Author hmr
     * @Description 根据帖子id获得帖子的详细信息
     * @Date: 2020/2/2 10:57
     * @param id
     * @param userId 当前用户
     * @reture: ArticleInfo
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
     * @reture: java.util.List<ArticleInfo>
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
     * @reture: ArticleInfo
     **/
    ArticleInfo getBriefById(Long id);

    /**
     *
     * @param id
     * @return
     */
    List<ArticleInfo> hotTopicListByCategory(Long id);

    /**
     * @Author hmr
     * @Description 根据id一部分信息
     * @Date: 2020/2/14 12:33
     * @param longs
     * @reture: java.util.List<ForumHotTopicInfo>
     **/
    List<ArticleInfo> selectInfoByTargetIds(Long[] longs);

    /**
     * @Author hmr
     * @Description 根据id查看简略信息    提供给热搜榜的
     * @Date: 2020/2/14 15:16
     * @param aLong
     * @reture: ArticleInfo
     **/
    ForumHotTopicInfo getBriefByHotTopicId(Long aLong);

    /**
     * @Author hmr
     * @Description 根据用户id获得用户的文章总数，获赞总数、收藏总数
     * @Date: 2020/2/15 15:34
     * @param userId
     * @reture: UserBriefDto
     **/
    UserBriefDto getUserBriefByUserId(Long userId);

    /**
     * @Author hmr
     * @Description 更新浏览次数
     * @Date: 2020/2/15 17:35
     * @param res
     * @reture: void
     **/
    void updateBrowserTimesFromRedis2Redis(List<BrowserTimesDto> res);

    /**
     * @Author hmr
     * @Description 将redis中储存的转发记录转存到db
     * @Date: 2020/2/23 19:35
     * @param res
     * @reture: void
     **/
    void updateForwardTimesFromRedis2DB(List<ForwardDto> res);

    /**
     * @Author hmr
     * @Description 根据用户选择的兴趣爱好获得文章列表
     * @Date: 2020/3/25 14:16
     * @param categoryId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.life.api.pojo.ArticleInfo>
     **/
    Message<List<ArticleInfo>> getBriefByCategoryId(Long categoryId, Integer pageSize, Integer currentPage) ;

    /**
     * @Author hmr
     * @Description 获得论坛模块排名
     * @Date: 2020/4/8 17:44
     * @param userId
     * @param pageSize
     * @param currentPage
     * @reture: java.lang.Long
     **/
    List<Long> getUserArticleRank(Integer pageSize, Integer currentPage);

    /**
     * @Author hmr
     * @Description 管理员删除文章
     * @Date: 2020/6/9 10:12
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> adminDeleteArticle(Long id);
}