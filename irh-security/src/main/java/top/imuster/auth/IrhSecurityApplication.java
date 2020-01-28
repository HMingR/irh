package top.imuster.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;

/**
 * @ClassName: IrhSecurityApplication
 * @Description: IrhSecurityApplication
 * @author: hmr
 * @date: 2020/1/27 16:44
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "top.imuster.user.api.service")
@EnableDiscoveryClient
public class IrhSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(IrhSecurityApplication.class, args);
    }

    @Bean
    @Primary
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
