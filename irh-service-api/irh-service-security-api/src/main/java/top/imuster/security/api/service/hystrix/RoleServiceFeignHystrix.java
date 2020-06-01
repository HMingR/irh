package top.imuster.security.api.service.hystrix;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.imuster.security.api.service.RoleServiceFeignApi;

/**
 * @ClassName: UserServiceFeignHystrix
 * @Description: UserServiceFeignHystrix
 * @author: hmr
 * @date: 2020/1/27 16:34
 */
@Component
public class RoleServiceFeignHystrix implements FallbackFactory<RoleServiceFeignApi> {

    private static final Logger log = LoggerFactory.getLogger(RoleServiceFeignHystrix.class);

    @Override
    public RoleServiceFeignApi create(Throwable throwable) {
        RoleServiceFeignApi roleServiceFeignApi = new RoleServiceFeignApi() {

            @Override
            public Boolean init() {
                return false;
            }
        };
        return roleServiceFeignApi;
    }
}
