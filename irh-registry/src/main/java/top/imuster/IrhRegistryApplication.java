package top.imuster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName: IrhRegistryApplication
 * @Description:
 * @author: lpf
 * @date: 2019/11/30 18:49
 */
@SpringBootApplication
@EnableEurekaServer
public class IrhRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(IrhRegistryApplication.class,args);
    }
}
