package top.imuster.common.core.annotation;

import top.imuster.common.core.enums.BrowserType;

import java.lang.annotation.*;

/**
 * @ClassName: BrowseRecordAnnotation
 * @Description: 浏览记录
 * @author: hmr
 * @date: 2020/4/26 15:19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BrowseRecordAnnotation {

    String value();

    BrowserType browserType();

}
