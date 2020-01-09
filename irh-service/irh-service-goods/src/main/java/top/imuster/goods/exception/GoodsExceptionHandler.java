package top.imuster.goods.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public Message goodsExceptionHandler(GoodsException exception){
        return Message.createByError(exception.getMessage());
    }

}
