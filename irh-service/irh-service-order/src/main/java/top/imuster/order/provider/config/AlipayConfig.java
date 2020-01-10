package top.imuster.order.provider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName: AlipayConfig
 * @Description:
 * @author: hmr
 * @date: 2019/12/22 21:58
 */
@PropertySource(value = "classpath:zfbinfo.properties", encoding = "utf-8")
@Component
public class AlipayConfig {
    @Value("${alipay.timeoutExpress}")
    private String timeoutExpress;

    public String getTimeoutExpress() {
        return timeoutExpress;
    }

    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }
}
