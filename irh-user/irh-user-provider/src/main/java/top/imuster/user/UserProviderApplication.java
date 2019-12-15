package top.imuster.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;
//import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: UserApplication
 * @Description: TODO
 * @author: lpf
 * @date: 2019/12/1 17:21
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserProviderApplication.class, args);
    }
}

