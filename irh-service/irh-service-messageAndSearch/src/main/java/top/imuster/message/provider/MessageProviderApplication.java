package top.imuster.message.provider;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName: MessageProviderApplication
 * @Description: 消息中心启动类
 * @author: hmr
 * @date: 2019/12/28 18:08
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"top.imuster.message", "top.imuster.common"})
@EnableFeignClients(basePackages = {"top.imuster.goods.api.service", "top.imuster.order.api.service"})
public class MessageProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageProviderApplication.class, args);
    }

    @Autowired
    @Bean
    public IndexRequestBuilder goodsIndexRequestBuilder(TransportClient transportClient){
        return transportClient.prepareIndex("goods", "goods");
    }

    @Autowired
    @Bean
    public IndexRequestBuilder articleIndexRequestBuilder(TransportClient transportClient){
        return transportClient.prepareIndex("article", "article");
    }

}
