package top.imuster.goods.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: ItemProviderApplication
 * @Description: TODO
 * @author: lpf
 * @date: 2019/12/1 15:21
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ItemProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItemProviderApplication.class, args);
    }
}
