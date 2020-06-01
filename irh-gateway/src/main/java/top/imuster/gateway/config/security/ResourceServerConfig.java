package top.imuster.gateway.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

/**
 * @author Administrator
 * @version 1.0
 **/
@Configuration
@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)//激活方法上的PreAuthorize注解
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ResourceServerConfig.class);

    @Value("${irh.security.publicKey}")
    private String publicKey;

    //定义JwtAccessTokenConverter，使用jwt令牌
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(publicKey);
        return converter;
    }

    //定义JwtTokenStore，使用jwt令牌
    @Bean
    public TokenStore jwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter);
        return jwtTokenStore;
    }

    @Bean
    FeignClientInterceptor feignClientInterceptor(){
        return new FeignClientInterceptor();
    }


    @Bean
    UrlAccessDecisionManager urlAccessDecisionManager() {
        return new UrlAccessDecisionManager();
    }

    @Bean
    ZuulUrlFilterInvocationSecurityMetadataSource zuulUrlFilterInvocationSecurityMetadataSource() {
        return new ZuulUrlFilterInvocationSecurityMetadataSource();
    }

    @Autowired
    CustomizedAuthenticationEntryPoint customizedAuthenticationEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        resources.authenticationEntryPoint(customizedAuthenticationEntryPoint);
    }

    //Http安全配置，对每个到达系统的http请求链接进行校验
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(zuulUrlFilterInvocationSecurityMetadataSource());
                        o.setAccessDecisionManager(urlAccessDecisionManager());
                        return o;
                    }
                });

        //所有请求必须认证通过
        http.authorizeRequests()
                //下边的路径放行
                .antMatchers("/swagger-resources","/swagger-resources/configuration/security",
                        "/swagger-ui.html","/webjars/**","/course/coursepic/list/**", "classpath:/resources/").permitAll()
                .anyRequest().authenticated();
    }
}
