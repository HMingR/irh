package top.imuster.user.provider.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.exception.GlobalExceptionHandler;

/**
 * @ClassName: UserExceptionHandler
 * @Description: user模块的异常处理类
 * @author: hmr
 * @date: 2019/12/19 20:14
 */
@RestControllerAdvice(basePackages = {"top.imuster.user.provider.web"})
public class UserExceptionHandler extends GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserExceptionHandler.class);

    @ExceptionHandler(UserException.class)
    @ResponseBody
    public Message serExceptionHandler(UserException exception){
        LOGGER.error("user模块出现的错误:",exception.getMessage(), exception);
        return Message.createByError(exception.getMessage());
    }
}
