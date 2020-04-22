package top.imuster.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName: GatewayExceptionHandler
 * @Description:
 * @author: hmr
 * @date: 2020/4/16 12:06
 */
@RestControllerAdvice
public class GatewayExceptionHandler {

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());


}
