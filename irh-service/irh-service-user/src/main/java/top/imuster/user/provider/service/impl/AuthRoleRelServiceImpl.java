package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.AuthRoleRel;
import top.imuster.user.provider.dao.AuthRoleRelDao;
import top.imuster.user.provider.service.AuthRoleRelService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AuthRoleRelService 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Service("authRoleRelService")
public class AuthRoleRelServiceImpl extends BaseServiceImpl<AuthRoleRel, Long> implements AuthRoleRelService {

    @Resource
    private AuthRoleRelDao authRoleRelDao;

    @Override
    public BaseDao<AuthRoleRel, Long> getDao() {
        return this.authRoleRelDao;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void editRoleAuth(AuthRoleRel authRoleRel) {
        Long roleId = authRoleRel.getRoleId();
        Long managementId = authRoleRel.getCreateManagementId();
        String ids = authRoleRel.getIds();

        List<Long> newAuthIds = new ArrayList<>();
        String[] split = ids.split(",");
        for (int i = 0; i < split.length; i++) newAuthIds.add(Long.parseLong(split[i]));
        List<Long> y = newAuthIds;

        AuthRoleRel condition = new AuthRoleRel();
        condition.setRoleId(roleId);
        List<AuthRoleRel> authRoleRels = authRoleRelDao.selectEntryList(condition);
        List<Long> originalAuthIds = authRoleRels.stream().map(AuthRoleRel::getAuthId).collect(Collectors.toList());
        List<Long> x = originalAuthIds;

        //x变成删除的id
        x.removeAll(newAuthIds);
        //y变成新增的id
        y.removeAll(originalAuthIds);

        condition.setRoleId(roleId);
        if(!x.isEmpty()){
            for (Long authId : x) {
                condition.setAuthId(authId);
                authRoleRelDao.deleteByCondition(condition);
            }
        }
        if(!y.isEmpty()){
            condition.setCreateManagementId(managementId);
            for (Long s : y) {
                condition.setAuthId(s);
                authRoleRelDao.insertEntry(condition);
            }
        }
    }
}