package top.imuster.life.provider.config;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ServletConfiguration
 * @Description: TODO
 * @author: hmr
 * @date: 2020/4/10 15:20
 */
@Configuration
public class ServletConfiguration {
    @Bean
    public ServletRegistrationBean druidStatViewServletBean() {
        // 后台的路径
        ServletRegistrationBean statViewServletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> params = new HashMap<>();
        // 账号密码，是否允许重置数据
        params.put("loginUsername", "admin");
        params.put("loginPassword", "admin");
        params.put("resetEnable", "true");
        statViewServletRegistrationBean.setInitParameters(params);
        return statViewServletRegistrationBean;
    }
}
