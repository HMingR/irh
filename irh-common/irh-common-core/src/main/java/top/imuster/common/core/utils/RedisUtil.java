package top.imuster.common.core.utils;

import org.apache.commons.lang3.StringUtils;
import top.imuster.common.base.config.GlobalConstant;

/**
 * @ClassName: RedisUtil
 * @Description: RedisUtil
 * @author: hmr
 * @date: 2019/12/15 15:59
 */
public class RedisUtil {

    public static String getAccessToken(String token){
        if(!StringUtils.isBlank(token)){
            return GlobalConstant.REDIS_ACCESS_TOKEN + token;
        }
        throw new RuntimeException("token为空");
    }


}
