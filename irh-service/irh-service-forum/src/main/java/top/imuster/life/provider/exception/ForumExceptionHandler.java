package top.imuster.life.provider.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.exception.GlobalExceptionHandler;

/**
 * @ClassName: ForumExceptionHandler
 * @Description: ForumExceptionHandler
 * @author: hmr
 * @date: 2020/2/1 17:42
 */
@RestControllerAdvice
public class ForumExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(ForumException.class)
    public Message<String> forumExceptionHandler(ForumException e){
        return Message.createByError(e.getMessage());
    }
}
