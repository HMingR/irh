package top.imuster.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName: AuthConfig
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/1 20:26
 */
@Configuration
public class AuthConfig {


    /**
     * @Description: 配置一个加密工具bean
     * @Author: hmr
     * @Date: 2019/12/1 20:34
     * @param
     * @reture: org.springframework.security.crypto.password.PasswordEncoder
     **/
    @Bean("passwordEncoder")
    @Primary
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
