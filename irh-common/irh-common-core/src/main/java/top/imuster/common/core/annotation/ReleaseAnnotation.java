package top.imuster.common.core.annotation;

import top.imuster.common.core.enums.OperationType;
import top.imuster.common.core.enums.ReleaseType;

import java.lang.annotation.*;

/**
 * @ClassName: ReleaseAnnotation
 * @Description: 发布或者删除(卖出)商品或者文章的时候需要将变化同步到ES中
 * @author: hmr
 * @date: 2020/4/23 19:45
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReleaseAnnotation {
    /**
     * @Author hmr
     * @Description 发布信息的类型
     * @Date: 2020/4/24 10:32
     * @param
     * @reture: top.imuster.common.core.enums.ReleaseType
     **/
    ReleaseType type();

    /**
     * @Author hmr
     * @Description EL表达式
     * @Date: 2020/4/24 10:32
     * @param
     * @reture: java.lang.String
     **/
    String value();

    /**
     * @Author hmr
     * @Description 对商品或者文章的操作
     * @Date: 2020/5/2 20:31
     * @param
     * @reture: top.imuster.common.core.enums.OperationType
     **/
    OperationType operationType();

    /**
     * @Author hmr
     * @Description EL表达式，发布信息的标签,用来保存在redis
     * @Date: 2020/5/7 20:03
     * @param
     * @reture: java.lang.String
     **/
    String tagNamesValue() default "";
}
