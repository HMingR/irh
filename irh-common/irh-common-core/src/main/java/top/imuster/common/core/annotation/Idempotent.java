package top.imuster.common.core.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: Idempotent
 * @Description: 控制幂等性
 * @author: hmr
 * @date: 2020/5/20 14:41
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

    /**
     * @Author hmr
     * @Description 限制提交次数
     * @Date: 2020/5/20 14:47
     * @param
     * @reture: int
     **/
    int submitTotal();

    /**
     * @Author hmr
     * @Description 时间
     * @Date: 2020/5/20 14:48
     * @param
     * @reture: int
     **/
    int timeTotal();


    /**
     * @Author hmr
     * @Description 时间单位
     * @Date: 2020/5/20 14:48
     * @param
     * @reture: java.util.concurrent.TimeUnit
     **/
    TimeUnit timeUnit();


    String msg() default "操作繁忙,请稍后再试";

}
