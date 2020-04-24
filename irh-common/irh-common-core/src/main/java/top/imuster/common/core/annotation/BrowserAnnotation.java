package top.imuster.common.core.annotation;

import top.imuster.common.core.enums.BrowserType;

import java.lang.annotation.*;

/**
 * @ClassName: BrowserAnnotation
 * @Description: 需要向redis中发送浏览次数的注解，使用该注解需要在形参中使用@PathValidate注解，查询的id为Long型
 * @author: hmr
 * @date: 2020/1/23 17:21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BrowserAnnotation {
    /**
     * @Author hmr
     * @Description 浏览对象的类型
     * @Date: 2020/1/23 17:35
     * @param
     * @reture: top.imuster.common.core.enums.BrowserType
     **/
    BrowserType browserType();

    /**
     * @Author hmr
     * @Description targetId
     * @Date: 2020/2/16 15:54
     * @param
     * @reture: java.lang.String
     **/
    String value() default "";

    /**
     * @Author hmr
     * @Description 是否禁用浏览记录统计功能
     * @Date: 2020/2/16 15:54
     * @param
     * @reture: boolean
     **/
    boolean disableBrowserTimes() default false;

    /**
     * @Author hmr
     * @Description 禁用热搜功能
     * @Date: 2020/2/16 15:55
     * @param
     * @reture: boolean
     **/
    boolean disableHotTopic() default false;

    /**
     * @Author hmr
     * @Description 热搜权值,不同的操作热搜也不一样,如浏览和点赞的热度值是不一样的
     * @Date: 2020/4/23 11:06
     * @param
     * @reture: int
     **/
    int hotTopicScore() default 1;

    /**
     * @Author hmr
     * @Description 是否禁用浏览记录功能，默认关闭
     * @Date: 2020/3/29 10:31
     * @param
     * @reture: boolean
     **/
    boolean disableBrowseRecord() default true;
}
