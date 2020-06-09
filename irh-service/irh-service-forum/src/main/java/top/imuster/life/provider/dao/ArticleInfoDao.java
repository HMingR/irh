package top.imuster.life.provider.dao;


import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.life.api.dto.ForwardDto;
import top.imuster.life.api.dto.UserBriefDto;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.pojo.ForumHotTopicInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ArticleInfoDao 接口
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public interface ArticleInfoDao extends BaseDao<ArticleInfo, Long> {
    //自定义扩展

    /**
     * @Author hmr
     * @Description selectListByCondition
     * @Date: 2020/2/1 19:45
     * @param articleInfo
     * @reture: java.util.List<ArticleInfo>
     **/
    List<ArticleInfo> selectListByCondition(ArticleInfo articleInfo);

    /**
     * @Author hmr
     * @Description 根据id查询点赞数
     * @Date: 2020/2/8 20:36
     * @param ids
     * @reture: java.util.List<ArticleInfo>
     **/
    List<ArticleInfo> selectUpTotalByIds(Long[] ids);

    /**
     * @Author hmr
     * @Description 根据id查询点赞数
     * @Date: 2020/2/9 10:38
     * @param id
     * @reture: java.lang.Long
     **/
    Long selectUpTotalById(Long id);

    /**
     * @Author hmr
     * @Description 根据id查询帖子的简略信息
     * @Date: 2020/2/11 16:24
     * @param id
     * @reture: ArticleInfo
     **/
    ArticleInfo selectBriefById(Long id);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/2/14 12:33
     * @param longs
     * @reture: java.util.List<ForumHotTopicInfo>
     **/
    List<ArticleInfo> selectInfoByTargetIds(Long[] longs);

    /**
     * @Author hmr
     * @Description 根据用户id获得用户文章的点赞总数
     * @Date: 2020/2/15 15:43
     * @param userId
     * @reture: java.lang.LongF
     *
     * @return*/
    UserBriefDto selectUserBriefTotalById(Long userId);

     /**
      * @Author hmr
      * @Description 根据分类id获得该分类下点赞数最大的5个
      * @Date: 2020/2/15 16:01
      * @param id
      * @reture: java.util.List<ArticleInfo>
      **/
    List<ArticleInfo> selectUpTop5ByCategoryId(Long id);

    /**
     * @Author hmr
     * @Description hmr
     * @Date: 2020/2/16 10:27
     * @param ids
     * @reture: java.util.Map<java.lang.Long,java.lang.Long>
     **/
    @MapKey("id")
    Map<Long, Long> selectBrowserTimesByIds(Long[] ids);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/2/16 10:38
     * @param list
     * @reture: void
     **/
    void updateBrowserTimesByCondition(@Param("list") List<ArticleInfo> list);

    /**
     * @Author hmr
     * @Description 批量
     * @Date: 2020/2/23 19:28
     * @param res
     * @reture: void
     **/
    void updateForwardTimesByCondition(@Param("list") List<ForwardDto> res);

   /**
     * @Author hmr
     * @Description 根据redis中的热搜id获得文章信息，并直接封装成一个ForumHotTopic对象
     * @Date: 2020/2/17 12:00
     * @param aLong
     * @reture: ForumHotTopicInfo
     **/
    ForumHotTopicInfo selectBriefByHotTopicId(Long aLong);

    /**
     * @Author hmr
     * @Description 根据给定的分类id获得文章简略信息
     * @Date: 2020/3/25 14:30
     * @param tagIds
     * @reture: java.util.List<top.imuster.life.api.pojo.ArticleInfo>
     **/
    List<ArticleInfo> selectArticleBriefByCategoryId(ArticleInfo articleInfo);

    /**
     * @Author hmr
     * @Description 根据用户发布文章获得的点赞，收藏，转发数量获得排名
     * @Date: 2020/4/8 17:46
     * @param userId
     * @param total
     * @reture: java.lang.Long
     **/
    List<Long> selectUserArticleRank(HashMap<String, Integer> total);

    /**
     * @Author hmr
     * @Description 插入文章信息，并返回id
     * @Date: 2020/4/12 9:59
     * @param articleInfo
     * @reture: java.lang.Long
     **/
    Long insertArticle(ArticleInfo articleInfo);

    /**
     * @Author hmr
     * @Description 根据文章id查看作者id
     * @Date: 2020/6/9 10:15
     * @param id
     * @reture: java.lang.Long
     **/
    Long selectUserIdById(Long id);
}