package top.imuster.user.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.security.api.pojo.AuthInfo;

/**
 * AuthInfoDao 接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface AuthInfoDao extends BaseDao<AuthInfo, Long> {
    //自定义扩展

    /**
     * @Description: 根据id查询authInfo
     * @Author: hmr
     * @Date: 2019/12/18 14:40
     * @param authInfoId
     * @reture: top.imuster.user.api.pojo.AuthInfo
     **/
    AuthInfo selectAuthInfoById(Long authInfoId) throws Exception;
}