package top.imuster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
/**
 * @Description:
 * @Author: lpf
 * @Date: 2019/11/24 20:32
 * @param null
 * @reture:
 **/
public class IrhRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(IrhRegistryApplication.class);
    }
}
