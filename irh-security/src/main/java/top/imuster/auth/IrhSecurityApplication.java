package top.imuster.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName: IrhSecurityApplication
 * @Description: IrhSecurityApplication
 * @author: hmr
 * @date: 2020/1/27 16:44
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "top.imuster.user.api.service")
@EnableDiscoveryClient
@ComponentScan(basePackages = {"top.imuster.common", "top.imuster.auth"})
public class IrhSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(IrhSecurityApplication.class, args);
    }

    @Bean
    @Primary
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
