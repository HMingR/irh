package top.imuster.file.provider;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName: IrhUploadApplication
 * @Description: TODO
 * @author: lpf
 * @date: 2019/11/30 21:11
 */
@SpringBootApplication
@EnableDiscoveryClient
public class IrhUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(IrhUploadApplication.class, args);
    }
}
