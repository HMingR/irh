package top.imuster.gateway.config;

import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.imuster.common.base.wrapper.Message;

/**
 * @ClassName: GatewayExceptionHandler
 * @Description:
 * @author: hmr
 * @date: 2020/4/16 12:06
 */
@RestControllerAdvice
public class GatewayExceptionHandler {

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ZuulException.class)
    public Message<String> exceptionHandler(Exception e){
        log.error("------->{}服务访问失败",e.getMessage());
        return Message.createByError("服务器暂时还没有启动,请稍后重试或联系管理员");
    }

}
