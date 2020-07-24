package top.imuster.goods;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.AntPathMatcher;

/**
 * @ClassName: GoodsProviderApplication
 * @Description:
 * @author: lpf
 * @date: 2019/12/1 15:21
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories
@EnableFeignClients(basePackages = {"top.imuster.order.api.service",
                                    "top.imuster.file.api.service",
                                    "top.imuster.user.api.service",
                                    "top.imuster.security.api.service"})
@ComponentScan(basePackages = {
        "top.imuster.common.core",
        "top.imuster"})
@EnableTransactionManagement
@EnableCaching
@EnableDistributedTransaction
public class GoodsProviderApplication {

    public static void main(String[] args) {
        try{

            SpringApplication.run(GoodsProviderApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Bean
    AntPathMatcher antPathMatcher(){
        return new AntPathMatcher();
    }
}
