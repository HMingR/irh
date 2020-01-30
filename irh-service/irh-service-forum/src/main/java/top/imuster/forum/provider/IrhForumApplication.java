package top.imuster.forum.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: IrhForumApplication
 * @Description: IrhForumApplication
 * @author: hmr
 * @date: 2020/1/30 15:33
 */
@SpringBootApplication
@EnableDiscoveryClient
public class IrhForumApplication {
    public static void main(String[] args) {
        SpringApplication.run(IrhForumApplication.class, args);
    }
}
