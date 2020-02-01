package top.imuster.common.core.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: NeedLogin
 * @Description: 被表示的方法表示需要登录才能访问
 * @author: hmr
 * @date: 2019/12/20 20:06
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedLogin {

    //是否需要校验，默认为false
    boolean validate() default true;

}
