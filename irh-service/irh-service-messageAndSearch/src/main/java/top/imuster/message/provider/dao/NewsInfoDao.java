package top.imuster.message.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.message.pojo.NewsInfo;

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

}