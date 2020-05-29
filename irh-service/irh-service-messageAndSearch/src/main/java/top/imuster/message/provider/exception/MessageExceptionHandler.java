package top.imuster.message.provider.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.exception.GlobalExceptionHandler;

/**
 * @ClassName: MessageExceptionHandler
 * @Description: 消息模块的异常处理类
 * @author: hmr
 * @date: 2020/1/18 11:19
 */
@RestControllerAdvice(basePackages = "top.imuster.message")
public class MessageExceptionHandler extends GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(MessageExceptionHandler.class);

    @ExceptionHandler({MessageException.class})
    public Message messageExceptionHandler(Exception e){
        log.error("message模块出现异常,异常信息为{}",e.getMessage());
        return Message.createByError(e.getMessage());
    }
}
