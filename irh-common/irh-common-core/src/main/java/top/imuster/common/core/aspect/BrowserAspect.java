package top.imuster.common.core.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;
import top.imuster.common.core.annotation.BrowserAnnotation;
import top.imuster.common.core.config.ExpressionEvaluator;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.utils.AspectUtil;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: BrowserTimesAspect
 * @Description: BrowserTimes的拦截器，实现向reids中新增浏览次数的逻辑
 * @author: hmr
 * @date: 2020/1/23 17:22
 */
@Component
@Aspect
public class BrowserAspect extends BaseController {

    @Value("${hot.topic.refreshTime:0}")
    private Long refreshTime;

    @Value("${browse.record.size:0}")
    private int browseRecordSize;

    @Value("${browse.record.expire:0}")
    private int browseRecordExpire;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Pointcut("@annotation(top.imuster.common.core.annotation.BrowserAnnotation)")
    public void pointCut(){}

    private ExpressionEvaluator<String> evaluator = new ExpressionEvaluator<>();

    @After("pointCut()")
    public void after(JoinPoint joinPoint){
        BrowserAnnotation annotation = getAnnotation(joinPoint);
        Long targetId = null;
        if(StringUtils.isNotBlank(annotation.value())) targetId = getTargetId(joinPoint);
        if(targetId == null || StringUtils.isBlank(annotation.value()))  targetId = AspectUtil.getTargetIdByPathVariable(joinPoint);
        BrowserType browserType = annotation.browserType();
        //处理浏览次数
        redisTemplate.opsForHash().increment(browserType.getRedisKeyHeader(), String.valueOf(targetId), 1);
        redisTemplate.expire(browserType.getRedisKeyHeader(), 30L, TimeUnit.MINUTES);
    }

    /**
     * @Author hmr
     * @Description 从方法中获得参数信息
     * @Date: 2020/1/26 16:12
     * @param joinPoint
     * @reture: top.imuster.common.core.annotation.BrowserAnnotation
     **/
    private BrowserAnnotation getAnnotation(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod().getAnnotation(BrowserAnnotation.class);
    }

    private Long getTargetId(JoinPoint joinPoint) {
        String condition = "";
        try{
            BrowserAnnotation handler = getAnnotation(joinPoint);
            if (joinPoint.getArgs() == null) {
                return null;
            }
            EvaluationContext evaluationContext = evaluator.createEvaluationContext(joinPoint.getTarget(), joinPoint.getTarget().getClass(), ((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs());
            AnnotatedElementKey methodKey = new AnnotatedElementKey(((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getTarget().getClass());
            condition = evaluator.condition(handler.value(), methodKey, evaluationContext, String.class);
            return Long.parseLong(condition);
        }catch (Exception e){
            log.error("注解中的EL表达式得到的结果解析失败,结果为{}", condition);
            return null;
        }
    }

}
