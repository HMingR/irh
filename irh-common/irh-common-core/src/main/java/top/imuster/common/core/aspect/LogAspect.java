package top.imuster.common.core.aspect;

import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import top.imuster.common.base.config.MessageCode;
import top.imuster.common.core.annotation.LogAnnotation;
import top.imuster.common.core.utils.RequestUtil;
import top.imuster.common.base.wrapper.Message;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Date;

/**
 * @ClassName: LogAspect
 * @Description: 日志的切面
 * @author: hmr
 * @date: 2019/12/14 15:09
 */

@Aspect
public class LogAspect {
    private ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Pointcut("@annotation(top.imuster.annotation.LogAnnotation)")
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
        Date startTime = this.threadLocal.get();
        Date endTime = new Date(System.currentTimeMillis());
        HttpServletRequest request = RequestUtil.getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String requestURI = request.getRequestURI();
        LogAnnotation annotation = getAnnotation(joinPoint);
        if(null == annotation){
            return;
        }

        //获得客户端的操作系统
        OperatingSystem operatingSystem = userAgent.getOperatingSystem();

        //获得浏览器名称
        String browserName = userAgent.getBrowser().getName();
        String remoteAddr = RequestUtil.getRemoteAddr(request);

    }

    public void setRequestData(){}

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
