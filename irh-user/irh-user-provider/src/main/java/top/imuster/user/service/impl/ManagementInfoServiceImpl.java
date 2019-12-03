package top.imuster.user.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.service.base.BaseServiceImpl;
import top.imuster.user.dao.ManagementInfoDao;
import top.imuster.user.pojo.ManagementInfo;
import top.imuster.user.pojo.RoleInfo;
import top.imuster.user.service.AuthRoleRelService;
import top.imuster.user.service.ManagementInfoService;
import top.imuster.user.service.ManagementRoleRelService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ManagementInfoService 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Service("managementInfoService")
public class ManagementInfoServiceImpl extends BaseServiceImpl<ManagementInfo, Long> implements ManagementInfoService {

    @Resource
    private ManagementInfoDao managementInfoDao;

    @Override
    public BaseDao<ManagementInfo, Long> getDao() {
        return this.managementInfoDao;
    }

    @Override
    public ManagementInfo getManagementRoleByCondition(ManagementInfo condition) throws Exception {
        return managementInfoDao.selectManagementRoleByCondition(condition);
    }

    @Override
    public ManagementInfo managementLoginByName(String name) throws Exception{
        ManagementInfo managementInfo = new ManagementInfo();
        managementInfo.setName(name);
        managementInfo.setState(2);
        return managementInfoDao.selectEntryList(managementInfo).get(0);
    }
}