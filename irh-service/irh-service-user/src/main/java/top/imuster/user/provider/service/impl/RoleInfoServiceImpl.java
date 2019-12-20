package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.AuthRoleRel;
import top.imuster.user.api.pojo.ManagementRoleRel;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.provider.dao.RoleInfoDao;
import top.imuster.user.provider.service.AuthRoleRelService;
import top.imuster.user.provider.service.ManagementRoleRelService;
import top.imuster.user.provider.service.RoleInfoService;

import javax.annotation.Resource;

/**
 * RoleInfoService 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Service("roleInfoService")
public class RoleInfoServiceImpl extends BaseServiceImpl<RoleInfo, Long> implements RoleInfoService {

    @Resource
    private RoleInfoDao roleInfoDao;

    @Resource
    private AuthRoleRelService authRoleRelService;

    @Resource
    private ManagementRoleRelService managementRoleRelService;

    @Override
    public BaseDao<RoleInfo, Long> getDao() {
        return this.roleInfoDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteById(Long roleId) {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setId(roleId);
        roleInfo.setState(1);
        roleInfoDao.updateByKey(roleInfo);

        AuthRoleRel authRoleRel = new AuthRoleRel();
        authRoleRel.setRoleId(roleId);
        authRoleRelService.deleteByCondtion(authRoleRel);

        ManagementRoleRel managementRoleRel = new ManagementRoleRel();
        managementRoleRel.setRoleId(roleId);
        managementRoleRelService.deleteByCondtion(managementRoleRel);
    }

    @Override
    public RoleInfo getRoleAndAuthByRoleId(Long roleId) {
        return roleInfoDao.selectRoleAndAuthByRoleId(roleId);
    }
}