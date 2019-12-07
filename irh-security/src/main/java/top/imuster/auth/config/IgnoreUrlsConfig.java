package top.imuster.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: IgnoreUrlsConfig
 * @Description: 用于配置不需要保护的资源路径
 * @author: hmr
 * @date: 2019/12/6 22:06
 */

@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
