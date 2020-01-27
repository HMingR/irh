package top.imuster.user.provider.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.ManagementInfo;
import top.imuster.user.api.pojo.UserRoleRel;
import top.imuster.user.provider.dao.ManagementInfoDao;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.ManagementInfoService;
import top.imuster.user.provider.service.UserRoleRelService;

import javax.annotation.Resource;

/**
 * ManagementInfoService 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Service("managementInfoService")
@Slf4j
public class ManagementInfoServiceImpl extends BaseServiceImpl<ManagementInfo, Long> implements ManagementInfoService {

    @Resource
    private ManagementInfoDao managementInfoDao;

    @Resource
    private UserRoleRelService userRoleRelService;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public BaseDao<ManagementInfo, Long> getDao() {
        return this.managementInfoDao;
    }

    @Override
    public ManagementInfo getManagementRoleByCondition(ManagementInfo condition){
        return managementInfoDao.selectManagementRoleByCondition(condition);
    }

    @Override
    public ManagementInfo loadManagementByName(String name) throws Exception{
        ManagementInfo managementInfo = new ManagementInfo();
        managementInfo.setName(name);
        managementInfo = managementInfoDao.selectManagementRoleByCondition(managementInfo);
        if(managementInfo == null) {
            throw new UserException("用户名或者密码错误");
        }
        if(managementInfo.getType() == null || managementInfo.getType() <= 20){
            throw new UserException("该账号已被冻结,请联系管理员");
        }
        return managementInfo;
    }

    @Override
    public void editManagementRoleById(Long managementId, String roleIds) throws Exception {
        String[] roles = roleIds.split(",");
        for (String role : roles) {
            UserRoleRel userRoleRel = new UserRoleRel();
            userRoleRel.setRoleId(Long.parseLong(role));
            userRoleRel.setStaffId(managementId);
            Integer count = userRoleRelService.getCountByCondition(userRoleRel);
            // todo 删除操作


            //新增操作
            if (count == 0) {
                // todo controller中传入当前用户
               // userRoleRel.setCreateManagemen(name);
                userRoleRelService.insertEntry(userRoleRel);
            }
        }
       return;
    }
}