package top.imuster.user.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;

import javax.websocket.server.PathParam;

/**
 * @ClassName: ManagementInfoFeignApi
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/19 19:12
 */
@FeignClient("user-service")
public interface ManagementInfoFeignApi {

    /**
     * @Description: 加载用户的所有信息
     * @Author: hmr
     * @Date: 2019/12/19 19:16
     * @param name
     * @reture: org.springframework.security.core.userdetails.UserDetails
     **/
    @GetMapping("/load/{name}")
    UserDetails loadManagementByName(@PathParam("name") String name);
}
