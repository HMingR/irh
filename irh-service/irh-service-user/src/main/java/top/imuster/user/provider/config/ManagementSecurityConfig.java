package top.imuster.user.provider.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.imuster.auth.config.BrowserSecurityConfig;
import top.imuster.user.provider.service.ManagementInfoService;
import top.imuster.user.provider.service.impl.ManagementInfoServiceImpl;

import javax.annotation.Resource;

/**
 * @ClassName: UserSecurityConfig
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/6 18:48
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ManagementSecurityConfig extends BrowserSecurityConfig {

    @Resource
    ManagementInfoService managementInfoService;

    @Bean
    public UserDetailsService userDetailsService(){
        return (username) -> managementInfoService.loadManagementByName(username);
    }

}
