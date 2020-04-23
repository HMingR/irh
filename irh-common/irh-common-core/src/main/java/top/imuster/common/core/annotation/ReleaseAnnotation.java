package top.imuster.common.core.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: ReleaseAnnotation
 * @Description: 发布商品或者文章的时候需要将数据保存到ES中
 * @author: hmr
 * @date: 2020/4/23 19:45
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReleaseAnnotation {


}
