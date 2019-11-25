package top.imuster.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName: MallProviderApplication
 * @Description: TODO
 * @author: hmr
 * @date: 2019/11/25 10:32
 */
@SpringBootApplication
@MapperScan("top.imuster")
public class MallProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallProviderApplication.class, args);
    }
}
