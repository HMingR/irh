package top.imuster.message.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.AntPathMatcher;

/**
 * @ClassName: MessageProviderApplication
 * @Description: 消息中心启动类
 * @author: hmr
 * @date: 2019/12/28 18:08
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"top.imuster.message", "top.imuster.common"})
@EnableFeignClients(basePackages = {"top.imuster.goods.api.service", "top.imuster.order.api.service", "top.imuster.security.api.service"})
public class MessageProviderApplication {
    public static void main(String[] args) {
        try{

            SpringApplication.run(MessageProviderApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Bean
    AntPathMatcher antPathMatcher(){
        return new AntPathMatcher();
    }
}
