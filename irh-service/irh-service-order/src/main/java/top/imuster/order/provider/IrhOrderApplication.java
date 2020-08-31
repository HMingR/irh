package top.imuster.order.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.AntPathMatcher;

/**
 * @ClassName: IrhOrderApplication
 * @Description:
 * @author: lpf
 * @date: 2019/12/18 17:11
 */
@SpringBootApplication
@ComponentScan(basePackages = {"top.imuster.order",
                               "top.imuster.common"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"top.imuster.goods.api.service",
                                    "top.imuster.order.api.service",
                                    "top.imuster.user.api.service"})
@EnableTransactionManagement
public class IrhOrderApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(IrhOrderApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Bean
    AntPathMatcher antPathMatcher(){
        return new AntPathMatcher();
    }
}
