package top.imuster.forum.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.forum.api.pojo.ArticleInfo;

import java.util.List;

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
     * @reture: java.util.List<top.imuster.forum.api.pojo.ArticleInfo>
     **/
    List<ArticleInfo> selectListByCondition(ArticleInfo articleInfo);

    /**
     * @Author hmr
     * @Description 根据id查询点赞数
     * @Date: 2020/2/8 20:36
     * @param ids
     * @reture: java.util.List<top.imuster.forum.api.pojo.ArticleInfo>
     **/
    List<ArticleInfo> selectUpTotalByIds(Long[] ids);
}