package top.imuster.life.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.life.api.pojo.ForumHotTopicInfo;

import java.util.List;
import java.util.Map;

/**
 * ForumHotTopicDao 接口
 * @author 黄明人
 * @since 2020-02-13 21:12:30
 */
public interface ForumHotTopicDao extends BaseDao<ForumHotTopicInfo, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 根据targetId从数据库中查找score
     * @Date: 2020/2/14 11:17
     * @param i
     * @reture: java.lang.Integer
     **/
    Long selectScoreByTargetId(Long i);

    /**
     * @Author hmr
     * @Description 根据目标id更新其score值
     * @Date: 2020/2/14 11:59
     * @param targetId
     * @param score
     * @reture: void
     **/
    void updateScoreByTargetId(ForumHotTopicInfo condition);

    /**
     * @Author hmr
     * @Description 获得score前topic的targetId和score
     * @Date: 2020/2/14 12:40
     * @param topic
     * @reture: java.util.List<ArticleInfo>
     **/
    List<ForumHotTopicInfo> selectMaxScoreTop(Map<String, Long> params);
}