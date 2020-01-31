package top.imuster.message.provider.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: ElasticsSearchConfig
 * @Description: ElasticsSearchConfig
 * @author: hmr
 * @date: 2020/1/31 9:39
 */
@Configuration
public class ElasticsSearchConfig {

    @Value("${irh.elasticsearch.host}")
    private String host;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        //创建RestHighLevelClient客户端
        return new RestHighLevelClient(RestClient.builder(getHttpHost()));
    }

    //项目主要使用RestHighLevelClient，对于低级的客户端暂时不用
    @Bean
    public RestClient restClient(){

        return RestClient.builder(getHttpHost()).build();
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
