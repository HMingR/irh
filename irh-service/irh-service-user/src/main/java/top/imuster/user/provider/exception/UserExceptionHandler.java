package top.imuster.user.provider.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.exception.GlobalExceptionHandler;

/**
 * @ClassName: UserExceptionHandler
 * @Description: user模块的异常处理类
 * @author: hmr
 * @date: 2019/12/19 20:14
 */
@RestControllerAdvice(basePackages = {"top.imuster"})
public class UserExceptionHandler extends GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(UserExceptionHandler.class);

    @ExceptionHandler(UserException.class)
    public Message<String> serExceptionHandler(UserException exception){
        log.error("user模块出现异常,异常信息为{}",exception.getMessage(), exception);
        return Message.createByError(exception.getMessage());
    }

//    //用户权限不足
//    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
//    public Message<String> accessDeniedExceptionHandler(){
//        return Message.createByError("您当前的权限不允许操作该资源");
//    }

}
