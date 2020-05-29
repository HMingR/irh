package top.imuster.auth.service.Impl;


import org.springframework.stereotype.Service;
import top.imuster.auth.dao.UserRoleRelDao;
import top.imuster.auth.service.UserRoleRelService;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.security.api.pojo.UserRoleRel;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserRoleRelService 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Service("userRoleRelService")
public class UserRoleRelServiceImpl extends BaseServiceImpl<UserRoleRel, Long> implements UserRoleRelService {

    @Resource
    private UserRoleRelDao userRoleRelDao;

    @Override
    public BaseDao<UserRoleRel, Long> getDao() {
        return this.userRoleRelDao;
    }

    @Override
    public Integer getCountByCondition(UserRoleRel userRoleRel) {
        return userRoleRelDao.selectEntryListCount(userRoleRel);
    }

    @Override
    public List<String> getRoleNameByUserName(String loginName) {
        return userRoleRelDao.selectRoleNameByUserName(loginName);
    }

}