package top.imuster.common.core.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class AspectUtil {
    /**
     * @Author hmr
     * @Description 从注解方法中获得需要查询的id
     * @Date: 2020/1/26 15:49
     * @param joinPoint
     * @reture: java.lang.Long
     **/
    public static Long getTargetId(JoinPoint joinPoint){
        Long result;
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        //这个参数中保存的是参数的值
        Object[] params = joinPoint.getArgs();
        if(params.length == 0){
            return null;
        }

        //这个里面保存的是参数的类型信息(字节码信息)
        Parameter[] parameters = method.getParameters();

        for(int i = 0; i < parameterAnnotations.length; i++){
            Object param = params[i];
            Annotation[] paramAnn = parameterAnnotations[i];
            if(param == null || paramAnn.length == 0){
                continue;
            }
            for (Annotation annotation : paramAnn){
                if(annotation.annotationType().equals(PathVariable.class)){
                    Parameter parameter = parameters[i];
                    System.out.println(parameter.getType().toString());
                    if(parameter.getType().toString().contains("java.lang.Long")){
                        result = (Long) params[i];
                        System.out.println("结果为" + result);
                        return result;
                    }
                }
            }
        }
        return null;
    }
}
