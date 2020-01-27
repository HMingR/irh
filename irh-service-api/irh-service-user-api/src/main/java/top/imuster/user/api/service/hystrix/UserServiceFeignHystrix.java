package top.imuster.user.api.service.hystrix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.pojo.ManagementInfo;
import top.imuster.user.api.service.UserServiceFeignApi;

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
    public ManagementInfo loadManagementByName(String name) {
        return null;
    }

    @Override
    public UserInfo loadConsumerInfoByEmail(String email) {
        return null;
    }
}
