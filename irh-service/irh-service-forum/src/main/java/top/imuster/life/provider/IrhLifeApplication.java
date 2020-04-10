package top.imuster.life.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName: IrhForumApplication
 * @Description: IrhForumApplication
 * @author: hmr
 * @date: 2020/1/30 15:33
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"top.imuster.file"})
@ComponentScan(basePackages = {"top.imuster.common",
                               "top.imuster.life",
                                "top.imuster.file.api.service.hystrix"})
@EnableTransactionManagement
public class IrhLifeApplication {
    public static void main(String[] args) {
        SpringApplication.run(IrhLifeApplication.class, args);
    }
}
