package top.imuster.user.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.service.base.BaseServiceImpl;
import top.imuster.user.dao.RoleInfoDao;
import top.imuster.user.pojo.RoleInfo;
import top.imuster.user.service.RoleInfoService;

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

    @Override
    public BaseDao<RoleInfo, Long> getDao() {
        return this.roleInfoDao;
    }
}