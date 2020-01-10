package top.imuster.file.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName: IrhFileApplication
 * @Description:
 * @author: lpf
 * @date: 2019/11/30 21:11
 */
@SpringBootApplication           //(exclude = DataSourceAutoConfiguration.class)    //排除数据库自动加载
@EnableEurekaClient
public class IrhFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(IrhFileApplication.class, args);
    }
}
