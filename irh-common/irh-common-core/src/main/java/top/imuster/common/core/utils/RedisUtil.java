package top.imuster.common.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.core.annotation.BrowserTimesAnnotation;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.exception.GlobalException;

/**
 * @ClassName: RedisUtil
 * @Description: 向redis中存储信息必须使用该工具类生成key，以便redis中的key可控
 * @author: hmr
 * @date: 2019/12/15 15:59
 */
public class RedisUtil {

    public static String getAccessToken(String token){
        if(!StringUtils.isEmpty(token)){
            return GlobalConstant.REDIS_ACCESS_TOKEN + token;
        }
        throw new RuntimeException("token为空");
    }

    /**
     * @Author hmr
     * @Description 获得会员注册时发送给会员的验证码
     * @Date: 2020/1/26 16:47
     * @param token
     * @reture: java.lang.String
     **/
    public static String getConsumerRegisterByEmailToken(String token){
        if(StringUtils.isNotEmpty(token)){
            return GlobalConstant.REDIS_CUSTOMER_EMAIL_REGISTER + token;
        }
        throw new GlobalException("token为空");
    }

    /**
     * @Author hmr
     * @Description 获得在redis中存储点赞记录的key
     * @Date: 2020/2/8 17:45
     * @param targetId
     * @param type
     * @param userId
     * @reture: java.lang.String
     **/
    public static String getUpKey(Long targetId, Integer type, Long userId) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(targetId).append("::").append(type).append("::").append(userId);
        return stringBuffer.toString();
    }

    /**
     * @Author hmr
     * @Description 获得在redis中存储的评论或留言的总数
     * @Date: 2020/2/8 18:42
     * @param targetId
     * @param type
     * @reture: java.lang.String
     **/
    public static String getUpTotalKey(Long targetId, Integer type){
        return new StringBuffer().append(targetId).append("::").append(type).toString();
    }

    /**
     * @Author hmr
     * @Description 根据errandInfo的主键id生成rediskey
     * @Date: 2020/2/12 11:35
     * @param targetId
     * @reture: java.lang.String
     **/
    public static String getErrandKey(Long targetId){
        return new StringBuffer().append(GlobalConstant.IRH_LIFE_ERRAND_KEY).append(targetId).toString();
    }
}
