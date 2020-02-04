package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.AuthInfo;
import top.imuster.user.api.pojo.AuthRoleRel;
import top.imuster.user.provider.dao.AuthInfoDao;
import top.imuster.user.provider.service.AuthInfoService;
import top.imuster.user.provider.service.AuthRoleRelService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<AuthInfo> tree(Long roleId) {

        //先获得该角色拥有的权限id
        AuthRoleRel authRoleRel = new AuthRoleRel();
        authRoleRel.setRoleId(roleId);
        List<AuthRoleRel> authRoleRels = authRoleRelService.selectEntryList(authRoleRel);
        List<Long> authIdList = authRoleRels.stream().map(AuthRoleRel::getAuthId).collect(Collectors.toList());

        //获得所有的权限
        AuthInfo condition = new AuthInfo();
        condition.setState(2);
        List<AuthInfo> allAuth = authInfoDao.selectEntryList(condition);
        List<AuthInfo> parents = new ArrayList<>();
        allAuth.stream().forEach(authInfo -> {
            if(authInfo.getParentId() == 0){
                parents.add(authInfo);
            }
        });
        for (AuthInfo parent : parents) {
            generateTree(allAuth, parent, authIdList);
        }
        return allAuth;
    }


    private void generateTree(List<AuthInfo> authInfoList, AuthInfo parent, List<Long> authIdList){
        for (AuthInfo authInfo : authInfoList) {
            if(authIdList.contains(authInfo.getId())){
                authInfo.setHave(2);
            }else{
                authInfo.setHave(1);
            }
            if(authInfo.getParentId() == parent.getId()){
                parent.getChilds().add(authInfo);
                generateTree(authInfoList, authInfo, authIdList);
            }
        }
    }
}