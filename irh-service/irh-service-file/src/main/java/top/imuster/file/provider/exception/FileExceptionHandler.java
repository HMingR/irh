package top.imuster.file.provider.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.exception.GlobalExceptionHandler;

/**
 * @ClassName: FileExceptionHandler
 * @Description: 文件模块的异常处理类
 * @author: hmr
 * @date: 2020/1/10 20:24
 */
@ControllerAdvice
public class FileExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(value = FileException.class)
    public Message fileExceptionHandler(FileException exception){
        return Message.createByError(exception.getMessage());
    }

}
