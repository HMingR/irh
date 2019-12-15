package top.imuster.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.imuster.interceptor.TokenInterceptor;

import javax.annotation.Resource;

/**
 * @ClassName: WebMvcConfig
 * @Description: 自定义web策略
 * @author: hmr
 * @date: 2019/12/15 20:23
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Resource
    TokenInterceptor tokenInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "*.js", "/**/*.js", "*.css", "/**/*.css", "*.html", "/**/*.html");
    }
}
