package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.annotation.MqGenerate;
import top.imuster.user.api.pojo.AuthInfo;
import top.imuster.user.api.pojo.AuthRoleRel;
import top.imuster.user.provider.dao.AuthInfoDao;
import top.imuster.user.provider.service.AuthInfoService;
import top.imuster.user.provider.service.AuthRoleRelService;

import javax.annotation.Resource;

/**
 * AuthInfoService 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Service("authInfoService")
public class AuthInfoServiceImpl extends BaseServiceImpl<AuthInfo, Long> implements AuthInfoService {

    @Resource
    private AuthInfoDao authInfoDao;

    @Resource
    private AuthRoleRelService authRoleRelService;

    @Override
    public BaseDao<AuthInfo, Long> getDao() {
        return this.authInfoDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteAuthById(Long authId) {
        AuthInfo authInfo = new AuthInfo();
        authInfo.setId(authId);
        authInfo.setState(1);
        authInfoDao.updateByKey(authInfo);

        AuthRoleRel authRoleRel = new AuthRoleRel();
        authRoleRel.setAuthId(authId);
        authRoleRelService.deleteByCondtion(authRoleRel);
    }
}