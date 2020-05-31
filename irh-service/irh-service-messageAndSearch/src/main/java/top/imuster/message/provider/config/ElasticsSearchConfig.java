package top.imuster.message.provider.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @ClassName: ElasticsSearchConfig
 * @Description: ElasticsSearchConfig
 * @author: hmr
 * @date: 2020/1/31 9:39
 */
@Configuration
public class ElasticsSearchConfig {

    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Value("${irh.elasticsearch.host}")
    private String host;

    @Value("${irh.elasticsearch.userName}")
    private String userName;

    @Value("${irh.elasticsearch.password}")
    private String password;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        HttpHost[] httpHosts = getHttpHost();
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
        RestClientBuilder builder = RestClient.builder(httpHosts).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                requestConfigBuilder.setConnectTimeout(-1);
                requestConfigBuilder.setSocketTimeout(-1);
                requestConfigBuilder.setConnectionRequestTimeout(-1);
                return requestConfigBuilder;
            }
        }).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.disableAuthCaching();
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
        }).setMaxRetryTimeoutMillis(5*60*1000);

        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }

    private HttpHost[] getHttpHost(){
        String[] split = host.split(",");
        //创建HttpHost数组，其中存放es主机和端口的配置信息
        HttpHost[] httpHostArray = new HttpHost[split.length];
        for(int i=0;i<split.length;i++){
            String item = split[i];
            httpHostArray[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]), "http");
        }
        return httpHostArray;
    }
}
