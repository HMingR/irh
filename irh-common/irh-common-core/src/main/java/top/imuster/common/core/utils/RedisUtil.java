package top.imuster.common.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: RedisUtil
 * @Description: RedisUtil
 * @author: hmr
 * @date: 2019/12/15 15:59
 */
public class RedisUtil {
    private static final String ACCESS_TOKEN = "irh:token:accessToken:";

    public static String getAccessToken(String token){
        if(!StringUtils.isBlank(token)){
            return ACCESS_TOKEN + token;
        }
        throw new RuntimeException("token为空");
    }


}
