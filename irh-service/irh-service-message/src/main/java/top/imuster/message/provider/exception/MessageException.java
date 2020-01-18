package top.imuster.message.provider.exception;

import top.imuster.common.core.exception.GlobalException;

/**
 * @ClassName: MessageException
 * @Description: 消息模块的异常处理类
 * @author: hmr
 * @date: 2020/1/18 11:18
 */
public class MessageException extends GlobalException {

    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }
}
