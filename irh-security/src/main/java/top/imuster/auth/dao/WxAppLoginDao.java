package top.imuster.auth.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.security.api.pojo.WxAppLoginInfo;

/**
 * WxAppLoginDao 接口
 * @author 黄明人
 * @since 2020-05-19 15:28:28
 */
public interface WxAppLoginDao extends BaseDao<WxAppLoginInfo, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description
     * @Date: 2020/5/19 15:35
     * @param openId
     * @reture: java.lang.Long
     **/
    Long selectUserIdByOpenId(String openId);

    /**
     * @Author hmr
     * @Description 解绑
     * @Date: 2020/6/3 10:50
     * @param userId
     * @reture: java.lang.Integer
     **/
    Integer updateInfoStateByUserId(Long userId);
}