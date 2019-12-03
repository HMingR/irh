package top.imuster.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName: BrowserSecurityConfig
 * @Description: web安全策略配置类
 * @author: hmr
 * @date: 2019/12/1 20:29
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin()
//                .loginPage("/browserLogin")//登录页
//                .loginProcessingUrl("") //登录请求的地址
//                .and()
//                .authorizeRequests()
//                .antMatchers("/browserLogin") //登录页
//                .permitAll()
//                .and()
//                .csrf().disable();

        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().logout().permitAll();
    }

    /**
     * @Description: 用户登录的密码校验
     * @Author: hmr
     * @Date: 2019/12/1 20:48
     * @param auth
     * @reture: void
     **/
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    }

}
