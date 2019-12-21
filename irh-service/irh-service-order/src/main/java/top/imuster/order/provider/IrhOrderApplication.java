package top.imuster.order.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: IrhOrderApplication
 * @Description: TODO
 * @author: lpf
 * @date: 2019/12/18 17:11
 */
@SpringBootApplication
@EnableDiscoveryClient
public class IrhOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(IrhOrderApplication.class, args);
    }
}
