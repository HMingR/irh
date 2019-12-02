package top.imuster.user.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.service.base.BaseServiceImpl;
import top.imuster.user.dao.AuthRoleRelDao;
import top.imuster.user.pojo.AuthRoleRel;
import top.imuster.user.service.AuthRoleRelService;

import javax.annotation.Resource;

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
}