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

    public static String getResetPwdByEmailToken(String token){
        if(!StringUtils.isNotEmpty(token)){
            return GlobalConstant.REDIS_RESET_PWD_EMAIL_TOKEN + token;
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
}
