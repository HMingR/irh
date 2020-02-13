package top.imuster.common.core.annotation;

import top.imuster.common.core.enums.BrowserType;

import java.lang.annotation.*;

/**
 * 热搜注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HotTopicAnnotation {

    /**
     * 浏览类型
     * @return
     */
    BrowserType browserType();

}
