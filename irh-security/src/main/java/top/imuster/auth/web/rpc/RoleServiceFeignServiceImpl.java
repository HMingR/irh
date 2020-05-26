package top.imuster.auth.web.rpc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.auth.service.RoleInfoService;
import top.imuster.security.api.pojo.RoleInfo;
import top.imuster.security.api.service.RoleServiceFeignApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: RoleServiceFeignServiceImpl
 * @Description: RoleServiceFeignServiceImpl
 * @author: hmr
 * @date: 2020/5/26 10:29
 */
@RestController
@RequestMapping("/security/feign")
public class RoleServiceFeignServiceImpl implements RoleServiceFeignApi {

    @Resource
    RoleInfoService roleInfoService;
    @Override
    public List<RoleInfo> getRoleAndAuthList() {
        return roleInfoService.getRoleAndAuthList();
    }
}
