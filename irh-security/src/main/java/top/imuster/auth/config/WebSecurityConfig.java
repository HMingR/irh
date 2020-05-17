package top.imuster.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import top.imuster.auth.component.*;
import top.imuster.auth.service.Impl.UsernameUserDetailsServiceImpl;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@Order(2147483636)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/emailCodeLogin", "/password","/login","/logout","/jwt", "/oneCard", "/identityCard", "/sendCode/**/**", "/resetPwd", "/pwdLogin");
    }

    @Bean
    public SmsAuthenticationProvider smsAuthenticationProvider(){
        SmsAuthenticationProvider provider = new SmsAuthenticationProvider();
        // 设置userDetailsService
        // 禁止隐藏用户未找到异常
        return provider;
    }

    @Bean
    public PwdAuthenticationProvider pwdAuthenticationProvider(){
        PwdAuthenticationProvider provider = new PwdAuthenticationProvider();
        return provider;
    }

    @Bean
    public PasswordAuthenticationProvider passwordAuthenticationProvider(){
        return new PasswordAuthenticationProvider();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //auth.authenticationProvider(smsAuthenticationProvider());
//        auth.authenticationProvider(pwdAuthenticationProvider());
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        ProviderManager authenticationManager = new ProviderManager(Arrays.asList(smsAuthenticationProvider(), pwdAuthenticationProvider(), passwordAuthenticationProvider()));
        return authenticationManager;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().and()
                .formLogin().failureHandler(irhAuthenticationFailHandler())
                .and()
                .authorizeRequests().anyRequest().authenticated();

    }

    @Bean
    public IrhAuthenticationFailHandler irhAuthenticationFailHandler(){
        return new IrhAuthenticationFailHandler();
    }

    @Bean
    public SmsCodeAuthenticationFilter smsCodeAuthenticationFilter(){
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        try {
            smsCodeAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(irhAuthenticationSuccessHandler());
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(irhAuthenticationFailHandler());
        return smsCodeAuthenticationFilter;
    }

    @Bean
    public PasswordAuthenticationFilter passwordAuthenticationFilter(){
        PasswordAuthenticationFilter smsCodeAuthenticationFilter = new PasswordAuthenticationFilter();
        try {
            smsCodeAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(irhAuthenticationSuccessHandler());
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(irhAuthenticationFailHandler());
        return smsCodeAuthenticationFilter;
    }

    @Bean
    public PwdAuthenticationFilter pwdAuthenticationFilter(){
        PwdAuthenticationFilter pwdAuthenticationFilter = new PwdAuthenticationFilter();
        try {
            pwdAuthenticationFilter.setAuthenticationManager(this.authenticationManager());
        } catch (Exception e) {
            e.printStackTrace();
        }
        pwdAuthenticationFilter.setAuthenticationSuccessHandler(irhAuthenticationSuccessHandler());
        pwdAuthenticationFilter.setAuthenticationFailureHandler(irhAuthenticationFailHandler());
        return pwdAuthenticationFilter;
    }

    @Bean
    public IrhAuthenticationSuccessHandler irhAuthenticationSuccessHandler(){
        return new IrhAuthenticationSuccessHandler();
    }

    @Bean
    public UsernameUserDetailsServiceImpl usernameUserDetailsService(){
        return new UsernameUserDetailsServiceImpl();
    }

}
