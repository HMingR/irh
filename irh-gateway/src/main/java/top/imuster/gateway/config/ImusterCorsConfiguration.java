package top.imuster.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/**
 * @Description: 网关配置类
 * @Author: lpf
 * @Date: 2019/12/17 21:00
 * @reture:
 **/
@Configuration
public class ImusterCorsConfiguration {

    @Bean
    public CorsFilter corsFilter() {

        //初始化cors配置对象
        CorsConfiguration configuration = new CorsConfiguration();

        //允许跨域的域名，如果要携带cookie，不能写*，*代表所有域名都可以跨域访问
        configuration.addAllowedOrigin("http://manage.imuster.top");
        configuration.addAllowedOrigin("http://www.imuster.top");
        configuration.setAllowCredentials(true);   //是否允许携带cookie
        configuration.addAllowedMethod("*");       //代表所有的请求方法：GET，POST，HEAD
        configuration.addAllowedHeader("*");       //代表允许携带任何头信息

        //初始化cors配置源对象
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",configuration);

        //返回corsFilter实例，参数：cors配置源对象
        return new CorsFilter(configurationSource);
    }

}
