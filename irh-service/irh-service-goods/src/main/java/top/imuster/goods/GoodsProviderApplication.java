package top.imuster.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: GoodsProviderApplication
 * @Description: TODO
 * @author: lpf
 * @date: 2019/12/1 15:21
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GoodsProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsProviderApplication.class, args);
    }
}
