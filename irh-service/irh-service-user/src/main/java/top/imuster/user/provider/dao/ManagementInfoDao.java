package top.imuster.user.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.user.api.pojo.ManagementInfo;

/**
 * ManagementInfoDao 接口
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
public interface ManagementInfoDao extends BaseDao<ManagementInfo, Long> {
    //自定义扩展
    ManagementInfo selectManagementRoleByCondition(ManagementInfo condition);
}