package top.imuster.order.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName: IrhOrderApplication
 * @Description:
 * @author: lpf
 * @date: 2019/12/18 17:11
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "top.imuster.goods.api.service")
public class IrhOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(IrhOrderApplication.class, args);
    }
}
