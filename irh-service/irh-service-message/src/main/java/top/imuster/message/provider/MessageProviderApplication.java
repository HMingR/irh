package top.imuster.message.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName: MessageProviderApplication
 * @Description: 消息中心启动类
 * @author: hmr
 * @date: 2019/12/28 18:08
 */
@SpringBootApplication
@EnableEurekaClient
public class MessageProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageProviderApplication.class, args);
    }
}
