package top.imuster.order.provider.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.imuster.common.core.config.BaseSwagger2Config;

/**
 * @ClassName: Swagger2Config
 * @Description: 订单模块的swagger2配置类
 * @author: hmr
 * @date: 2019/12/28 14:45
 */
@Configuration
@EnableSwagger2
public class Swagger2Config extends BaseSwagger2Config {
    public Swagger2Config(){
        super("top.imuster.order.provider.web.controller");
    }
}
