package top.imuster.auth.service.Impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.auth.dao.RoleInfoDao;
import top.imuster.auth.service.AuthRoleRelService;
import top.imuster.auth.service.RoleInfoService;
import top.imuster.auth.service.UserRoleRelService;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.security.api.pojo.AuthRoleRel;
import top.imuster.security.api.pojo.RoleInfo;
import top.imuster.security.api.pojo.UserRoleRel;

import javax.annotation.Resource;
import java.util.List;

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
    private UserRoleRelService userRoleRelService;

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

        UserRoleRel userRoleRel = new UserRoleRel();
        userRoleRel.setRoleId(roleId);
        userRoleRelService.deleteByCondtion(userRoleRel);
    }

    @Override
    public RoleInfo getRoleAndAuthByRoleId(Long roleId) {
        return roleInfoDao.selectRoleAndAuthByRoleId(roleId);
    }

    @Override
    public RoleInfo getRoleAndAuthByRoleName(String name) {
        return roleInfoDao.selectRoleAndAuthByRoleName(name);
    }

    @Override
    public List<RoleInfo> getRoleAndAuthList() {
        return roleInfoDao.selectRoleAndAuth();
    }

    @Override
    public List<RoleInfo> getOtherRoleByAdminId(Long adminId) {
        return roleInfoDao.selectOtherRoleByAdminId(adminId);
    }

    @Override
    public List<String> getRoleNameByUserName(String loginName) {
        return userRoleRelService.getRoleNameByUserName(loginName);
    }
}