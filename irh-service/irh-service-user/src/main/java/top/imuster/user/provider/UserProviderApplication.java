package top.imuster.user.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextListener;

/**
 * @ClassName: UserApplication
 * @Description:
 * @author: lpf
 * @date: 2019/12/1 17:21
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"top.imuster.goods.api.service","top.imuster.order.api.service"})
@ComponentScan(basePackages = {
        "top.imuster.auth",
        "top.imuster.common.core.aspect",
        "top.imuster.user.provider",
        "top.imuster.common.core.config",
        "top.imuster.common.core.interceptor"})
public class UserProviderApplication {
    public static void main(String[] args) {

        SpringApplication.run(UserProviderApplication.class, args);
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    @Bean
    public AntPathMatcher antPathMatcher(){
        return new AntPathMatcher();
    }

    @Bean
    @Primary
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

