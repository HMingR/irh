package top.imuster.auth.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.imuster.auth.component.SmsUserDetailsService;

import javax.annotation.Resource;

/**
 * @ClassName: SmsCodeAuthenticationSecurityConfig
 * @Description: TODO
 * @author: hmr
 * @date: 2020/4/30 20:16
 */
//@Component
public class SmsCodeAuthenticationSecurityConfig /*extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> */{

    @Resource
    private IrhAuthenticationSuccessHandler irhAuthenticationSuccessHandler;

    @Resource
    private IrhAuthenticationFailHandler irhAuthenticationFailHandler;

    @Resource
    SmsUserDetailsService smsUserDetailsService;

   // @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();

        //设置AuthenticationManager
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        //设置成功失败处理器
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(irhAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(irhAuthenticationFailHandler);
        //设置provider
        SmsAuthenticationProvider smsCodeAuthenticationProvider = new SmsAuthenticationProvider();
        smsCodeAuthenticationProvider.setSmsUserDetailsService(smsUserDetailsService);
        http.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
