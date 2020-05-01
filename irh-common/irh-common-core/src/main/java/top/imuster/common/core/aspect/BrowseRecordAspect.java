package top.imuster.common.core.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;
import top.imuster.common.core.annotation.BrowseRecordAnnotation;
import top.imuster.common.core.config.ExpressionEvaluator;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.dto.BrowseRecordDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.utils.RedisUtil;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: BrowseRecordAspect
 * @Description: 处理浏览记录
 * @author: hmr
 * @date: 2020/4/26 15:20
 */
@Component
@Aspect
public class BrowseRecordAspect extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(BrowseRecordAspect.class);

    @Pointcut("@annotation(top.imuster.common.core.annotation.BrowseRecordAnnotation)")
    public void pointCut(){}

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RedisTemplate redisTemplate;

    ExpressionEvaluator<BrowseRecordDto> evaluator = new ExpressionEvaluator();

    @After("pointCut()")
    public void after(JoinPoint joinPoint){
        BrowseRecordAnnotation annotation = getAnnotation(joinPoint);
        BrowseRecordDto recordDto = new BrowseRecordDto();
        if(StringUtils.isNotBlank(annotation.value())) recordDto = getRecordInfo(joinPoint);
        if(recordDto == null || recordDto.getUserId() == null) return;    //当没有得到方法形参中的recordDto或者recordDto中的userId为null时，直接返回
        BrowserType browserType = recordDto.getBrowserType();
        Long userId = recordDto.getUserId();
        if(userId == null || browserType == null) return;
        String recordKey = RedisUtil.getBrowseRecordKey(browserType, userId);
        try{
            redisTemplate.opsForList().leftPush(recordKey, objectMapper.writeValueAsString(recordDto));
        }catch (Exception e){
            log.error("将浏览记录序列化失败");
        }
        redisTemplate.expire(recordKey, 180, TimeUnit.DAYS);
        Long size = redisTemplate.opsForList().size(recordKey);
        if(size > 30) redisTemplate.opsForList().rightPop(recordKey);
    }


    /**
     * @Author hmr
     * @Description 从方法中获得参数信息
     * @Date: 2020/1/26 16:12
     * @param joinPoint
     * @reture: top.imuster.common.core.annotation.BrowserAnnotation
     **/
    private BrowseRecordAnnotation getAnnotation(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod().getAnnotation(BrowseRecordAnnotation.class);
    }

    private BrowseRecordDto getRecordInfo(JoinPoint joinPoint) {
        BrowseRecordDto condition = null;
        try{
            BrowseRecordAnnotation handler = getAnnotation(joinPoint);
            if (joinPoint.getArgs() == null) {
                return null;
            }
            EvaluationContext evaluationContext = evaluator.createEvaluationContext(joinPoint.getTarget(), joinPoint.getTarget().getClass(), ((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getArgs());
            AnnotatedElementKey methodKey = new AnnotatedElementKey(((MethodSignature) joinPoint.getSignature()).getMethod(), joinPoint.getTarget().getClass());
            condition = evaluator.condition(handler.value(), methodKey, evaluationContext, BrowseRecordDto.class);
            return condition;
        }catch (Exception e){
            log.error("注解中的EL表达式得到的结果解析失败,结果为{}", condition);
            return null;
        }
    }
}
