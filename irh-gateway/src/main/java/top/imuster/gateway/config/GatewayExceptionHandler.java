package top.imuster.gateway.config;

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

    @ExceptionHandler(value = Exception.class)
    public Message<String> ExceptionHand(){
        return Message.createByError("稍等片刻,服务正在启动");
    }
}
