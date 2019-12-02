package top.imuster.user.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.service.base.BaseServiceImpl;
import top.imuster.user.dao.ManagementRoleRelDao;
import top.imuster.user.pojo.ManagementRoleRel;
import top.imuster.user.service.ManagementRoleRelService;

import javax.annotation.Resource;

/**
 * ManagementRoleRelService 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Service("managementRoleRelService")
public class ManagementRoleRelServiceImpl extends BaseServiceImpl<ManagementRoleRel, Long> implements ManagementRoleRelService {

    @Resource
    private ManagementRoleRelDao managementRoleRelDao;

    @Override
    public BaseDao<ManagementRoleRel, Long> getDao() {
        return this.managementRoleRelDao;
    }
}