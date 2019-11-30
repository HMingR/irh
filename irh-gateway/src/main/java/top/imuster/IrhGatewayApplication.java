package top.imuster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @ClassName: IrhGatewayApplication
 * @Description: TODO
 * @author: lpf
 * @date: 2019/11/30 19:11
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class IrhGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(IrhGatewayApplication.class,args);
    }
}

