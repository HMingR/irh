package top.imuster.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.exception.GlobalExceptionHandler;

/**
 * @ClassName: SecurityExceptionHandler
 * @Description: SecurityExceptionHandler
 * @author: hmr
 * @date: 2020/1/27 16:03
 */
@RestControllerAdvice
@Slf4j
public class SecurityExceptionHandler extends GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public Message usernameNotFoundExceptionHandler(UsernameNotFoundException e){
        log.error("登录失败{}",e.getMessage());
        return Message.createByError("用户名或密码错误");
    }

    @ExceptionHandler(AuthenticationException.class)
    public Message authenticationExceptionHandler(AuthenticationException e){
        log.error("登录异常{}",e.getMessage(),e);
        return Message.createByError("用户名或密码错误");
    }
}
