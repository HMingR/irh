package top.imuster.common.core.aspect;

import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.config.MessageCode;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.LogAnnotation;
import top.imuster.common.core.dto.OperationLogDto;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.utils.CusThreadLocal;
import top.imuster.common.core.utils.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Date;

/**
 * @ClassName: LogAspect
 * @Description: 日志的切面
 * @author: hmr
 * @date: 2019/12/14 15:09
 */

@Slf4j
@Aspect
@Component
public class LogAspect {

    private ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Pointcut("@annotation(top.imuster.common.core.annotation.LogAnnotation)")
    public void logAnnotation(){}


    /**
     * @Description: 执行方法之前
     * @Author: hmr
     * @Date: 2019/12/14 15:18
     * @reture: void
     **/
    @Before("logAnnotation()")
    public void before(){
        this.threadLocal.set(new Date(System.currentTimeMillis()));
    }

    /**
     * @Description: 返回方法之后
     * @Author: hmr
     * @Date: 2019/12/14 15:18
     * @param
     * @reture: void
     **/
    @AfterReturning(value = "logAnnotation()", returning = "returnValue")
    public void afterReturning(JoinPoint joinPoint, Object returnValue){
        if(returnValue instanceof Message){
            Message message =(Message) returnValue;
            if(null != message && message.getCode() == MessageCode.SUCCESS.getCode()){
                this.handleLog(joinPoint, message);
            }
        }
    }

    private void handleLog(JoinPoint joinPoint, Message message){
        try{

            Date startTime = this.threadLocal.get();
            Date endTime = new Date(System.currentTimeMillis());
            HttpServletRequest request = RequestUtil.getRequest();
            UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
            String requestURI = request.getRequestURI();
            LogAnnotation annotation = getAnnotation(joinPoint);
            if(null == annotation){
                return;
            }
            UserDto loginUser = (UserDto) CusThreadLocal.get(GlobalConstant.USER_TOKEN_DTO);
            OperationLogDto operationLogDto = new OperationLogDto();
            //获得客户端的操作系统
            OperatingSystem os = userAgent.getOperatingSystem();

            //获得浏览器名称
            String browserName = userAgent.getBrowser().getName();
            String remoteAddr = RequestUtil.getRemoteAddr(request);

            operationLogDto.setBrowser(browserName);
            operationLogDto.setIp(remoteAddr);
            operationLogDto.setClassName(joinPoint.getClass().getName());
            operationLogDto.setMethodName(joinPoint.getSignature().getName());
            operationLogDto.setStartTime(startTime);
            operationLogDto.setEndTime(endTime);
            operationLogDto.setRequestUrl(requestURI);
            operationLogDto.setExcuteTime(endTime.getTime() - startTime.getTime());
            operationLogDto.setGroupName(loginUser.getGroupName());
            operationLogDto.setCreator(loginUser.getLoginName());
            operationLogDto.setCreatorId(loginUser.getUserId());
            operationLogDto.setOs(os.getName());
            //获得日志的类型
            operationLogDto.setLogType(annotation.logType().getType());
            operationLogDto.setLogName(annotation.logType().getName());

            getControllerMethodDescription(annotation, operationLogDto, message, joinPoint);
        }catch (Exception e){
            log.error("通过注解设置日志失败{}",e.getMessage(),e);
        }

    }

    public void getControllerMethodDescription(LogAnnotation logAnnotation, OperationLogDto operationLogDto, Object result, JoinPoint joinPoint) throws Exception{
        if(logAnnotation.isSaveRequestData()){
            setRequestData(operationLogDto, joinPoint);
        }
        if(logAnnotation.isSaveResponseData()){
            setResponseData(operationLogDto, result);
        }

    }

    /**
     * @Description: 保存请求数据
     * @Author: hmr
     * @Date: 2019/12/20 19:43
     * @param
     * @reture: void
     **/
    public void setRequestData(OperationLogDto operationLogDto, JoinPoint joinPoint) throws Exception{
        Object[] args = joinPoint.getArgs();
        if(args.length == 0){
            return;
        }
        Object[] parameter = new Object[args.length];
        Integer index = 0;
        for (Object arg : args) {
            if(arg instanceof HttpServletRequest){
                continue;
            }
            parameter[index] = arg;
            index++;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String desc = objectMapper.writeValueAsString(parameter);
        operationLogDto.setDescription(desc);

    }


    /**
     * @Description: 保存响应数据
     * @Author: hmr
     * @Date: 2019/12/20 19:43
     * @param
     * @reture: void
     **/
    public void setResponseData(OperationLogDto operationLogDto, Object result){
        try {
            operationLogDto.setResponseData(String.valueOf(result));
        } catch (Exception e) {
            log.error("获取响应数据,出现错误={}", e.getMessage(), e);
        }
    }

    /**
     * @Description: 判断方法的注解上是否有LogAnnotation注解,如果有则返回该注解，如果没有，则返回null
     * @Author: hmr
     * @Date: 2019/12/14 20:48
     * @param joinPoint
     * @reture: top.imuster.common.core.annotation.LogAnnotation
     **/
    private static LogAnnotation getAnnotation(JoinPoint joinPoint){
        Annotation[] declaredAnnotations = joinPoint.getTarget().getClass().getDeclaredAnnotations();
        for (Annotation annotation : declaredAnnotations) {
            if(annotation instanceof LogAnnotation){
                return (LogAnnotation) annotation;
            }
        }
        return null;
    }

}
