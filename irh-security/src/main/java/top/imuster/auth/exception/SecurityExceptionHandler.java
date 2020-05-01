package top.imuster.auth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
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
@RestControllerAdvice(basePackages = "top.imuster.auth")
public class SecurityExceptionHandler extends GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(SecurityExceptionHandler.class);


    @ExceptionHandler(UsernameNotFoundException.class)
    public Message<String> usernameNotFoundExceptionHandler(UsernameNotFoundException e){
        log.error("登录失败{}",e.getMessage());
        return Message.createByError("登录异常");
    }

    @ExceptionHandler(AuthenticationException.class)
    public Message<String> authenticationExceptionHandler(AuthenticationException e){
        log.error("登录异常{}",e.getMessage(),e);
        return Message.createByError("登录异常");
    }

    @ExceptionHandler(CustomSecurityException.class)
    public Message<String> securityExceptionHandler(CustomSecurityException e){
        return Message.createByError(e.getMessage());
    }

    @ExceptionHandler(InvalidGrantException.class)
    public Message<String> invalidGrantExceptionException(){
        return Message.createByError("登录异常");
    }
}
