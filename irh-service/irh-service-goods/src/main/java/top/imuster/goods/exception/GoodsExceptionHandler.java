package top.imuster.goods.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.exception.GlobalExceptionHandler;

/**
 * @ClassName: GoodsExceptionHandler
 * @Description:
 * @author: hmr
 * @date: 2019/12/22 10:50
 */
@RestControllerAdvice
@Slf4j
public class GoodsExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(GoodsException.class)
    public Message goodsExceptionHandler(GoodsException exception){
        log.error("goods模块出现异常", exception.getMessage(), exception);
        return Message.createByError();
    }

}
