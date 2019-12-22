package top.imuster.goods.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.exception.GlobalExceptionHandler;

/**
 * @ClassName: GoodsExceptionHandler
 * @Description: TODO
 * @author: hmr
 * @date: 2019/12/22 10:50
 */
@ControllerAdvice
public class GoodsExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(GoodsException.class)
    public Message goodsExceptionHandler(GoodsException exception){
        String message = exception.getMessage();
        return Message.createByError(message);
    }

}
