package top.imuster.life.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName: IrhLifeServiceApplication
 * @Description: IrhLifeServiceApplication
 * @author: hmr
 * @date: 2020/2/11 14:54
 */
@SpringBootApplication
@EnableEurekaClient
public class IrhLifeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(IrhLifeServiceApplication.class, args);
    }
}
