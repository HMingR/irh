package top.imuster.user.provider.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import top.imuster.auth.component.RestAuthenticationEntryPoint;
import top.imuster.auth.component.RestfulAccessDeniedHandler;
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
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ManagementSecurityConfig extends BrowserSecurityConfig {

}
