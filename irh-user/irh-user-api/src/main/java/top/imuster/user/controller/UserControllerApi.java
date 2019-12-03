package top.imuster.user.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import top.imuster.wrapper.Message;

/**
 * @ClassName: top.imuster.user.controller.UserControllerApi
 * @Description: 用户接口
 * @author: hmr
 * @date: 2019/12/3 9:50
 */

@FeignClient(value = "user-service")
@Component("userControllerClient")
public interface UserControllerApi {

    @GetMapping("/login")
    Message managementLogin();

}
