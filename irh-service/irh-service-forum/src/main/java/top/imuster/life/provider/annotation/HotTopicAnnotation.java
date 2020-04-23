package top.imuster.life.provider.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: HotTopicAnnotation
 * @Description: 热搜
 * @author: hmr
 * @date: 2020/4/23 11:08
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HotTopicAnnotation {

    //
    String targetId();

    int score();

}
