package top.imuster.user.dao;

import top.imuster.domain.base.BaseDao;
import top.imuster.user.pojo.AuthInfo;

/**
 * AuthInfoDao 接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface AuthInfoDao extends BaseDao<AuthInfo, Long> {
    //自定义扩展

    AuthInfo selectAuthInfoById(Long authInfoId) throws Exception;
}