package top.imuster.life.provider.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.imuster.common.base.wrapper.Message;

/**
 * @ClassName: LifeExceptionHandler
 * @Description: LifeExceptionHandler
 * @author: hmr
 * @date: 2020/2/12 11:43
 */
@ControllerAdvice
public class LifeExceptionHandler {

    @ExceptionHandler(LifeException.class)
    public Message<String> lifeExceptionHanld(LifeException e){
        return Message.createByError(e.getMessage());
    }
}
