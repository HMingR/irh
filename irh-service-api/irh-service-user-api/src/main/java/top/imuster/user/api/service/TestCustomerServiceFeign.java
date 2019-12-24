package top.imuster.user.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName: TestCustomerServiceFeign
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/24 22:00
 */
@FeignClient("user-service")
public interface TestCustomerServiceFeign {

    @GetMapping("/user/test")
    void test();
}
