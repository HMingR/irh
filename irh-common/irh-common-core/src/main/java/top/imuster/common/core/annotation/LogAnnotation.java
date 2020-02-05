package top.imuster.common.core.annotation;


import top.imuster.common.core.enums.LogTypeEnum;

import java.lang.annotation.*;

/**
 * @ClassName: LogAnnotation
 * @Description: 需要记录操作日志的注解
 * @author: hmr
 * @date: 2019/12/14 14:54
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    LogTypeEnum logType();

    /**
     * @Description: 是否保存请求参数
     * @Author: hmr
     * @Date: 2019/12/14 15:08
     * @param
     * @reture: boolean
     **/
    boolean isSaveRequestData() default false;

    /**
     * @Description: 是否保存响应参数
     * @Author: hmr
     * @Date: 2019/12/14 15:08
     * @param
     * @reture: boolean
     **/
    boolean isSaveResponseData() default false;
}
