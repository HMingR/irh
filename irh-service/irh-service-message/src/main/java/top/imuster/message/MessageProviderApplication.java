package top.imuster.message;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName: MessageProviderApplication
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/28 18:08
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,DruidDataSourceAutoConfigure.class})
@EnableEurekaClient
public class MessageProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageProviderApplication.class, args);
    }
}
