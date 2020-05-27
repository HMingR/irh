package top.imuster.common.core.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: CheckIdentityAnnotation
 * @Description: 校验用户是否认证
 * @author: hmr
 * @date: 2020/5/27 12:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CheckIdentityAnnotation {
}
