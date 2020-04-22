package top.imuster.goods.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
public class GoodsExceptionHandler extends GlobalExceptionHandler {
    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(GoodsException.class)
    public Message goodsExceptionHandler(GoodsException exception){
        log.error("goods模块出现异常{}", exception.getMessage(), exception);
        return Message.createByError(exception.getMessage());
    }

}
