package top.imuster.common.core.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Message handleValidationExceptionHandler(MethodArgumentNotValidException exception){
        String message = exception.getBindingResult().getAllErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining());
        logger.error("错误信息为", message);
        return Message.createByError(message);
    }

    //处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Message constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        logger.error("错误信息为", message);
        return Message.createByError(message);
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Message bindExceptionHandler(BindException exception){
        String defaultMessage = exception.getAllErrors().get(0).getDefaultMessage();
        logger.error("错误信息为", defaultMessage);
        return Message.createByError(defaultMessage);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public Message validationExceptionHandler(ValidationException exception){
        String message = exception.getMessage();
        logger.error("错误信息为", message);
        return Message.createByError(message);
    }

    @ExceptionHandler(NeedLoginException.class)
    @ResponseBody
    public Message needLoginExceptionHandler(NeedLoginException exception){
        return Message.createByError(exception.getMessage());
    }

    /**
     * @Description: 空指针异常
     * @Author: hmr
     * @Date: 2020/1/11 21:34
     * @param e
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ExceptionHandler(NullPointerException.class)
    public Message nullPointerExceptionHandler(NullPointerException e){
        logger.error("服务器出现空指针异常", e.getMessage(), e);
        return Message.createByError("服务器内部出现异常," + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Message runtimeExceptionHandler(RuntimeException e){
        logger.error("服务器内部出现运行时异常",e.getMessage(), e);
        return Message.createByError("服务器内部出现异常," + e.getMessage());
    }

    //类型转换异常
    @ExceptionHandler(ClassCastException.class)
    public Message classCastExceptionHandler(ClassCastException e) {
        return Message.createByError("服务器内部出现异常," + e.getMessage());
    }

    //json解析异常
    @ExceptionHandler(JsonProcessingException.class)
    public Message jsonProcessingExceptionHandler(JsonProcessingException e){
        return Message.createByError("服务器内部错误,解析json失败");
    }

    @ExceptionHandler(Exception.class)
    public Message exception(){
        return Message.createByError("服务器出现未知错误");
    }

    @ExceptionHandler(GlobalException.class)
    public Message globalExceptionHandler(){
        return Message.createByError("服务器内部错误");
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
