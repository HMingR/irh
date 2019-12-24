package top.imuster.order.provider.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.exception.GlobalExceptionHandler;

/**
 * @ClassName: OrderExceptionHandler
 * @Description: 订单模块的异常处理类
 * @author: hmr
 * @date: 2019/12/23 11:47
 */
@ControllerAdvice
@Slf4j
public class OrderExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(OrderException.class)
    @ResponseBody
    public Message orderExceptionHandler(OrderException exception){
        log.error("订单模块出现了错误:{}", exception.getMessage(), exception);
        return Message.createByError(exception.getMessage());
    }
}
