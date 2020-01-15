package top.imuster.common.core.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: MqGenerate
 * @Description: 发送消息的方法上面标注该注解,使用该注解的时候必须在方法的参数中添加 SendMessageDto，并且在使用方法中需要设置sendMessageDto的值
 * @author: hmr
 * @date: 2020/1/13 15:44
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MqGenerate {

    //是否将消息体中的内容保存在redis中
    boolean isSaveToRedis() default false;

    //一个消息实体还是多个消息实体
    boolean one() default true;

}
