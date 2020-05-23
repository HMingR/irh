package top.imuster.message.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.message.pojo.NewsInfo;

import java.util.HashMap;
import java.util.List;

/**
 * NewsInfoDao 接口
 * @author 黄明人
 * @since 2020-01-17 17:13:09
 */
public interface NewsInfoDao extends BaseDao<NewsInfo, Long> {
    //自定义扩展

    /**
     * @Author hmr
     * @Description 查看回复信息
     * @Date: 2020/5/8 15:39
     * @param newsInfo
     * @reture: java.util.List<top.imuster.message.pojo.NewsInfo>
     **/
    List<NewsInfo> selectAtMeMessage(NewsInfo newsInfo);

    /**
     * @Author hmr
     * @Description 查看有多少条消息
     * @Date: 2020/5/8 16:01
     * @param newsInfo
     * @reture: java.lang.Integer
     **/
    Integer selectAtMeTotal(NewsInfo newsInfo);

    /**
     * @Author hmr
     * @Description 根据id获得接收者id
     * @Date: 2020/5/8 16:14
     * @param id
     * @reture: java.lang.Long
     **/
    Long selectReceiverIdById(Long id);

    /**
     * @Author hmr
     * @Description 根据sourceId改变状态
     * @Date: 2020/5/22 9:56
     * @param newsInfo
     * @reture: java.lang.Integer
     **/
    Integer updateStateBySourceId(NewsInfo newsInfo);

    /**
     * @Author hmr
     * @Description 获得系统未读的总数
     * @Date: 2020/5/22 10:06
     * @param newsInfo
     * @reture: java.lang.Integer
     **/
    Integer selectSystemUnreadTotal(NewsInfo newsInfo);

    /**
     * @Author hmr
     * @Description 获得at我的未读数量
     * @Date: 2020/5/22 10:11
     * @param newsInfo
     * @reture: java.lang.Integer
     **/
    Integer selectAtMeUnreadTotal(NewsInfo newsInfo);

    /**
     * @Author hmr
     * @Description 根据userId和type将所有的信息都标称已读 type取值  1-系统通知   2-at我的
     * @Date: 2020/5/22 10:32
     * @param param
     * @reture: java.lang.Integer
     **/
    Integer updateStateByUserId(HashMap<String, String> param);

    /**
     * @Author hmr
     * @Description 获得用户的系统通知总数
     * @Date: 2020/5/22 11:06
     * @param userId
     * @reture: java.lang.Integer
     **/
    Integer selectSystemNewsTotalByUserId(Long userId);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/5/22 11:09
     * @param newsInfo
     * @reture: java.util.List<top.imuster.message.pojo.NewsInfo>
     **/
    List<NewsInfo> selectSystemNewsByPage(NewsInfo newsInfo);
}