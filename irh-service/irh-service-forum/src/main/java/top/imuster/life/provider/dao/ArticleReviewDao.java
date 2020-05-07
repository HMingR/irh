package top.imuster.life.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.life.api.pojo.ArticleReviewInfo;

import java.util.List;

/**
 * ArticleReviewDao 接口
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public interface ArticleReviewDao extends BaseDao<ArticleReviewInfo, Long> {
    //自定义扩展

    /**
     * @Author hmr
     * @Description 根据回复id获得用户id
     * @Date: 2020/2/5 10:44
     * @param targetId
     * @reture: java.lang.Long
     **/
    Long selectUserIdByReviewId(Long targetId);


    /**
     * @Author hmr
     * @Description
     * @Date: 2020/2/8 20:42
     * @param reviewIds
     * @reture: java.util.List<ArticleReviewInfo>
     **/
    List<ArticleReviewInfo> selectUpTotalByIds(Long[] reviewIds);

    /**
     * @Author hmr
     * @Description 根据id获得点赞数
     * @Date: 2020/2/9 10:42
     * @param id
     * @reture: java.lang.Long
     **/
    Long selectUpTotalById(Long id);

    /**
     * @Author hmr
     * @Description 根据文章信息查找一级留言信息
     * @Date: 2020/4/10 17:18
     * @param searchCondition
     * @reture: java.util.List<top.imuster.life.api.pojo.ArticleReviewInfo>
     **/
    List<ArticleReviewInfo> selectFirstClassReviewByArticleId(ArticleReviewInfo searchCondition);

    /**
     * @Author hmr
     * @Description 根据parentId查询该记录的作者
     * @Date: 2020/5/7 8:45
     * @param parentId
     * @reture: java.lang.Long
     **/
    Long selectParentWriterIdById(Long parentId);
}