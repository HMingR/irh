package top.imuster.common.core.utils;

import org.apache.commons.lang3.StringUtils;
import top.imuster.common.base.config.GlobalConstant;
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
     * @Description 获得在redis中存储的评论或留言的总数的key
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

    /**
     * 根据BrowserType获得redis中保存在Zset中的key
     * @param browserType
     * @return
     */
    public static String getHotTopicKey(BrowserType browserType){
        if(browserType.getType() == 1) return GlobalConstant.IRH_ES_HOT_TOPIC_ZSET_KEY;
        if(browserType.getType() == 2) return GlobalConstant.IRH_DEMAND_HOT_TOPIC_ZSET_KEY;
        if(browserType.getType() == 3) return GlobalConstant.IRH_FORUM_HOT_TOPIC_ZSET_KEY;
        throw new GlobalException("传入的BrowserType有误");
    }

    /**
     * @Author hmr
     * @Description 根据浏览类型和用户id生成存储浏览历史的redis key
     * @Date: 2020/3/28 9:54
     * @param browserType
     * @param userId
     * @reture: java.lang.String
     **/
    public static String getBrowseRecordKey(BrowserType browserType, Long userId){
        if(browserType.getType() == 1) return new StringBuilder().append(GlobalConstant.IRH_BROWSE_RECORD_LIST).append("1::").append(userId).toString();
        if(browserType.getType() == 3) return new StringBuilder().append(GlobalConstant.IRH_BROWSE_RECORD_LIST).append("3::").append(userId).toString();
        throw new GlobalException("参数错误");
    }

    /**
     * @Author hmr
     * @Description 根据慈善基金申请id把自动生成的订单id保存到redis里面，该方法生成key
     * @Date: 2020/4/15 15:42
     * @param valueOf
     * @reture: java.lang.Object
     **/
    public static String getDonationApplyCode(String applyId) {
        return new StringBuffer().append(GlobalConstant.IRH_ORDER_DONATION).append(applyId).toString();
    }
}
