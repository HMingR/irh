package top.imuster.user.provider.exception;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public Message serExceptionHandler(UserException exception){
        log.error("user模块出现异常,异常信息为{}",exception.getMessage(), exception);
        return Message.createByError(exception.getMessage());
    }

    //用户权限不足
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public Message accessDeniedExceptionHandler(){
        return Message.createByError("您当前的权限不允许操作该资源");
    }
}
