package top.imuster.user.provider.web.rpc;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.ManagementInfo;
import top.imuster.user.api.service.ManagementInfoFeignApi;
import top.imuster.user.provider.dao.ManagementInfoDao;
import top.imuster.user.provider.service.ManagementInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: ManagementInfoFeignClient
 * @Description: 提供给远程调用
 * @author: hmr
 * @date: 2019/12/19 19:17
 */
@RestController
public class ManagementInfoFeignClient extends BaseServiceImpl<ManagementInfo, Long> implements ManagementInfoFeignApi {

    @Resource
    ManagementInfoDao managementInfoDao;

    @Resource
    ManagementInfoService managementInfoService;

    @Override
    public BaseDao<ManagementInfo, Long> getDao() {
        return this.managementInfoDao;
    }

    @Override
    public UserDetails loadManagementByName(String name) {
        return managementInfoService.loadManagementByName(name);
    }
}
