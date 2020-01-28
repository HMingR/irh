package top.imuster.user.api.service.hystrix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.UserServiceFeignApi;

import java.util.List;

/**
 * @ClassName: UserServiceFeignHystrix
 * @Description: UserServiceFeignHystrix
 * @author: hmr
 * @date: 2020/1/27 16:34
 */
@Component
@Slf4j
public class UserServiceFeignHystrix implements UserServiceFeignApi {

    @Override
    public UserInfo loadUserInfoByEmail(String email) {
        return null;
    }

    @Override
    public List<RoleInfo> getAllRoleAndAuth() {
        return null;
    }
}
