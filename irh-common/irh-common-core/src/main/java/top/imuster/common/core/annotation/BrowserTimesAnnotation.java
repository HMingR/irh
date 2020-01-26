package top.imuster.common.core.annotation;

import top.imuster.common.core.enums.BrowserType;

import java.lang.annotation.*;

/**
 * @ClassName: BrowserTimesAnnotation
 * @Description: 需要向redis中发送浏览次数的注解，使用该注解需要在形参中使用@PathValidate注解，查询的id为Long型
 * @author: hmr
 * @date: 2020/1/23 17:21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BrowserTimesAnnotation {
    /**
     * @Author hmr
     * @Description 浏览对象的类型
     * @Date: 2020/1/23 17:35
     * @param
     * @reture: top.imuster.common.core.enums.BrowserType
     **/
    BrowserType browserType();
}
