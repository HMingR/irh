package top.imuster.user.config.management;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.imuster.auth.config.BrowserSecurityConfig;
import top.imuster.user.pojo.ManagementInfo;
import top.imuster.user.service.ManagementInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: MallSecurityConfig
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/6 18:48
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MallSecurityConfig extends BrowserSecurityConfig {

    @Resource
    ManagementInfoService managementInfoService;


    @Bean
    public UserDetailsService userDetailsService(){
        return (username) -> managementInfoService.loadManagementByName(username);
    }

}
