package top.imuster.user.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.user.api.pojo.ReportFeedbackInfo;

import java.util.HashMap;
import java.util.List;

/**
 * ReportFeedbackInfoDao 接口
 * @author 黄明人
 * @since 2020-01-11 12:12:11
 */
public interface ReportFeedbackInfoDao extends BaseDao<ReportFeedbackInfo, Long> {
    //自定义扩展
    List<ReportFeedbackInfo> selectListByCondition(ReportFeedbackInfo condition);

    /**
     * @Author hmr
     * @Description 处理举报
     * @Date: 2020/2/29 15:17
     * @param param
     * @reture: void
     **/
    void updateByTargetId(ReportFeedbackInfo condition);

    /**
     * @Author hmr
     * @Description 根据targetId获得所有关于该id的举报信息
     * @Date: 2020/3/1 11:19
     * @param targetId
     * @reture: java.util.List<top.imuster.user.api.pojo.ReportFeedbackInfo>
     **/
    List<ReportFeedbackInfo> selectAllReportByTargetId(HashMap<String, String> params);
}