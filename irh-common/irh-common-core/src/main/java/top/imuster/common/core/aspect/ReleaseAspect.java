package top.imuster.common.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.annotation.ReleaseAnnotation;
import top.imuster.common.core.config.ExpressionEvaluator;
import top.imuster.common.core.dto.SendReleaseDto;
import top.imuster.common.core.enums.ReleaseType;
import top.imuster.common.core.utils.GenerateSendMessageService;

import javax.annotation.Resource;

/**
 * @ClassName: ReleaseAspect
 * @Description: 将发布的信息保存到mq中，然后再从mq中保存到es中
 * @author: hmr
 * @date: 2020/4/24 10:24
 */
@Component
@Aspect
public class ReleaseAspect {

    private static final Logger log = LoggerFactory.getLogger(ReleaseAspect.class);

    private ExpressionEvaluator<BaseDomain> evaluator = new ExpressionEvaluator<>();

    @Pointcut("@annotation(top.imuster.common.core.annotation.ReleaseAnnotation)")
    public void pointCut(){}

    @Resource
    GenerateSendMessageService generateSendMessageService;

    /**
     * @Author hmr
     * @Description 只有发布成功也就是没有报错的时候才执行该方法
     * @Date: 2020/4/24 10:27
     * @param joinPoint
     * @reture: void
     **/
    @AfterReturning("pointCut()")
    public void afterReturning(JoinPoint joinPoint){
        log.info("进入releaseAspect");
        BaseDomain targetInfo = getTargetInfo(joinPoint);
        if(targetInfo == null){
            log.warn("----->用户发布信息时没有从方法中获得指定的信息");
            return;
        }
        ReleaseAnnotation annotation = getAnnotation(joinPoint);
        ReleaseType type = annotation.type();
        SendReleaseDto sendReleaseDto = new SendReleaseDto();
        sendReleaseDto.setTargetInfo(targetInfo);
        sendReleaseDto.setReleaseType(type);
        generateSendMessageService.sendToMq(sendReleaseDto);
    }

    /**
     * @Author hmr
     * @Description 获得发布的信息
     * @Date: 2020/4/24 10:28
     * @param joinPoint
     * @reture: top.imuster.common.base.domain.BaseDomain
     **/
    public BaseDomain getTargetInfo(JoinPoint joinPoint){
        BaseDomain target;
        try{
            ReleaseAnnotation handler = getAnnotation(joinPoint);
            if (joinPoint.getArgs() == null) {
                return null;
            }
            EvaluationContext evaluationContext = evaluator.createEvaluationContext(joinPoint.getTarget(), joinPoint.getTarget().getClass(), ((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs());
            AnnotatedElementKey methodKey = new AnnotatedElementKey(((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getTarget().getClass());
            target = evaluator.condition(handler.value(), methodKey, evaluationContext, BaseDomain.class);
            log.info("aspect中获得的信息为:{}", target);
            return target;
        }catch (Exception e){
            log.error("--------->releaseAnnotation获得发布信息时出现错误,错误信息为{}", e.getMessage());
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
    private ReleaseAnnotation getAnnotation(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod().getAnnotation(ReleaseAnnotation.class);
    }
}
