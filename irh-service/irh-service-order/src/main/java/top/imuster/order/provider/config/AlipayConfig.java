package top.imuster.order.provider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: AlipayConfig
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/22 21:58
 */
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {
    private String openApiDomain;
    private String mcloudApiDomain;
    private String pid;
    private String appId;
    private String privateKey;
    private String publicKey;
    private String alipayPublicKey;
    private String signType;
    private String maxQueryRetry;
    private String queryDuration;
    private String maxCancelRetry;
    private String cancelDuration;
    private String heartbeatDelay;
    private String heartbeatDuration;

    public String getOpenApiDomain() {
        return openApiDomain;
    }

    public String getMcloudApiDomain() {
        return mcloudApiDomain;
    }

    public String getPid() {
        return pid;
    }

    public String getAppId() {
        return appId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public String getSignType() {
        return signType;
    }

    public String getMaxQueryRetry() {
        return maxQueryRetry;
    }

    public String getQueryDuration() {
        return queryDuration;
    }

    public String getMaxCancelRetry() {
        return maxCancelRetry;
    }

    public String getCancelDuration() {
        return cancelDuration;
    }

    public String getHeartbeatDelay() {
        return heartbeatDelay;
    }

    public String getHeartbeatDuration() {
        return heartbeatDuration;
    }
}
