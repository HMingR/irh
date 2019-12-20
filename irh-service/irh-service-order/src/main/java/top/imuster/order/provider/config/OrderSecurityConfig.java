package top.imuster.order.provider.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import top.imuster.auth.config.BrowserSecurityConfig;

/**
 * @ClassName: OrderSecurityConfig
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/20 18:12
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OrderSecurityConfig extends BrowserSecurityConfig {
}
