package top.imuster.order.provider.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.exception.GlobalExceptionHandler;

/**
 * @ClassName: OrderExceptionHandler
 * @Description: 订单模块的异常处理类
 * @author: hmr
 * @date: 2019/12/23 11:47
 */
@ControllerAdvice
public class OrderExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(OrderException.class)
    public Message orderExceptionHandler(OrderException exception){
        return Message.createByError(exception.getMessage());
    }
}
