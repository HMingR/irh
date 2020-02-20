package top.imuster.life.provider.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.imuster.common.core.config.BaseSwagger2Config;

/**
 * @ClassName: Swagger2Config
 * @Description: Swagger2Config的配置类
 * @author: hmr
 * @date: 2019/12/7 15:29
 */
@Configuration
@EnableSwagger2
public class Swagger2Config extends BaseSwagger2Config {

    private static String BASE_PACKAGE = "top.imuster";

    public Swagger2Config(){
        super(BASE_PACKAGE);
    }

}
