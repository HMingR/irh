package top.imuster.common.core.exception;

import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.imuster.common.base.config.MessageCode;
import top.imuster.common.base.wrapper.Message;

import javax.validation.*;
import java.util.stream.Collectors;


/**
 * @ClassName: GlobalExceptionHandler
 * @Description: 全局异常处理器
 * @author: hmr
 * @date: 2019/12/19 14:46
 */
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Message<String> handleValidationExceptionHandler(MethodArgumentNotValidException exception){
        String message = exception.getBindingResult().getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining());
        log.error("错误信息为{}", message);
        return Message.createByCustom(MessageCode.ILLEGAL_ARGUMENT_CODE);
    }

    //处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Message<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        log.error("错误信息为{}", message);
        return Message.createByCustom(MessageCode.ILLEGAL_ARGUMENT_CODE);
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Message<String> bindExceptionHandler(BindException exception){
        String defaultMessage = exception.getAllErrors().get(0).getDefaultMessage();
        log.error("错误信息为{}", defaultMessage);
        return Message.createByError(defaultMessage);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public Message<String> validationExceptionHandler(ValidationException exception){
        String message = exception.getMessage();
        return Message.createByCustom(MessageCode.ILLEGAL_ARGUMENT_CODE, message);
    }

    @ExceptionHandler(NeedLoginException.class)
    @ResponseBody
    public Message<String> needLoginExceptionHandler(NeedLoginException exception){
        return Message.createByCustom(MessageCode.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Message<String> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception){
        return Message.createByError("参数异常");
    }

    @ExceptionHandler(Exception.class)
    public Message exception(Exception e){
        log.error("服务器出现未知错误,错误信息为{}",e.getMessage(),e);
        return Message.createByError("服务器出现未知错误，请稍后重试或联系管理员");
    }

    @ExceptionHandler(GlobalException.class)
    public Message globalExceptionHandler(GlobalException e){
        return Message.createByError(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Message httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException exception){
        log.error("请求方式错误{}",exception.getMessage());
        return Message.createByError("非法的请求方式");
    }


    /**
     * @Description: 配置校验规则，当有很多数据需要校验的时候，一个出现问题，则停止校验，直接抛出异常
     * @Author: hmr
     * @Date: 2019/12/19 20:50
     * @param
     * @reture:
     **/
    @Configuration
    class config{
        @Bean
        public Validator validator(){
            ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                    .configure()
                    .failFast(true)
                    .buildValidatorFactory();
            return validatorFactory.getValidator();
        }

        @Bean
        public MethodValidationPostProcessor methodValidationPostProcessor() {
            MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
            methodValidationPostProcessor.setValidator(validator());
            return methodValidationPostProcessor;
        }
    }

}
