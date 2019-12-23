package top.imuster.order.provider.exception;

import top.imuster.common.core.exception.GlobalException;

/**
 * @ClassName: OrderException
 * @Description: 订单模块的异常类
 * @author: hmr
 * @date: 2019/12/23 11:47
 */
public class OrderException extends GlobalException {
    OrderException(){}

    public OrderException(String message){super(message);}
}
