package top.imuster.life.provider.component;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;
import top.imuster.common.core.config.ExpressionEvaluator;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.utils.AspectUtil;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.life.provider.annotation.HotTopicAnnotation;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: HotTopicAspect
 * @Description: HotTopicAspect
 * @author: hmr
 * @date: 2020/4/26 9:57
 */
@Component
@Aspect
public class HotTopicAspect {

    @Value("${hot.topic.refreshTime:0}")
    private Long refreshTime;

    private static final Logger log = LoggerFactory.getLogger(HotTopicAspect.class);

    @Autowired
    RedisTemplate redisTemplate;

    @Pointcut("@annotation(top.imuster.life.provider.annotation.HotTopicAnnotation)")
    public void pointCut(){}

    private ExpressionEvaluator<String> evaluator = new ExpressionEvaluator<>();

    @After("pointCut()")
    public void afterAdvice(JoinPoint joinPoint){
        HotTopicAnnotation annotation = getAnnotation(joinPoint);
        Long targetId = null;
        if(StringUtils.isNotBlank(annotation.targetId())) targetId = getTargetId(joinPoint);

        //当通过es表达式拿不到值的时候再通过@PathVariable注解尝试获得值
        if(targetId == null || StringUtils.isBlank(annotation.targetId()))  targetId = AspectUtil.getTargetIdByPathVariable(joinPoint);

        //还是没有获得值或者小于1的时候则直接返回
        if(targetId == null || targetId.intValue() < 1) return;
        int score = annotation.score();
        String zSetKey = RedisUtil.getHotTopicKey(BrowserType.FORUM);
        redisTemplate.opsForZSet().incrementScore(zSetKey, String.valueOf(targetId), score);
        redisTemplate.expire(zSetKey, refreshTime, TimeUnit.MINUTES);

    }

    private Long getTargetId(JoinPoint joinPoint) {
        String condition = "";
        try{
            HotTopicAnnotation handler = getAnnotation(joinPoint);
            if (joinPoint.getArgs() == null) {
                return null;
            }
            EvaluationContext evaluationContext = evaluator.createEvaluationContext(joinPoint.getTarget(), joinPoint.getTarget().getClass(), ((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs());
            AnnotatedElementKey methodKey = new AnnotatedElementKey(((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getTarget().getClass());
            condition = evaluator.condition(handler.targetId(), methodKey, evaluationContext, String.class);
            return Long.parseLong(condition);
        }catch (Exception e){
            log.error("注解中的EL表达式得到的结果解析失败,结果为{}", condition);
            return null;
        }
    }

    /**
     * @Author hmr
     * @Description 从方法中获得参数信息
     * @Date: 2020/1/26 16:12
     * @param joinPoint
     * @reture: top.imuster.common.core.annotation.BrowserAnnotation
     **/
    private HotTopicAnnotation getAnnotation(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod().getAnnotation(HotTopicAnnotation.class);
    }

}
