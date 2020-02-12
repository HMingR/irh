package top.imuster.common.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName: RequestUtil
 * @Description: request的工具类
 * @author: hmr
 * @date: 2019/12/14 16:04
 */
public class RequestUtil {
    protected static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    @Autowired
    RedisTemplate redisTemplate;

    public static final String X_REAL_IP = "X-Real-IP";
    public static final String UNKNOWN = "unknown";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String LOCALHOST_IP = "127.0.0.1";
    public static final String LOCALHOST_IP_16 = "0:0:0:0:0:0:0:1";
    public static final int MAX_IP_LENGTH = 15;

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }


    /**
     * @Description: 获得用户的真实ip地址
     * @Author: hmr
     * @Date: 2019/12/14 20:30
     * @param request
     * @reture: java.lang.String
     **/
    public static String getRemoteAddr(HttpServletRequest request){
        String ipAddress = request.getHeader(X_REAL_IP);
        if(StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader(X_FORWARDED_FOR);
        }
        if(StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader(WL_PROXY_CLIENT_IP);
        }
        if(StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader(HTTP_CLIENT_IP);
        }
        if(StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader(HTTP_X_FORWARDED_FOR);
        }
        if(StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader(PROXY_CLIENT_IP);
        }
        if(StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)){
            ipAddress = request.getRemoteAddr();
        }
        if(StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)){
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST_IP.equals(ipAddress) || LOCALHOST_IP_16.equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    logger.error("获取IP地址, 出现异常={}", e.getMessage(), e);
                }
                assert inet != null;
                ipAddress = inet.getHostAddress();
            }
            logger.info("获取IP地址 ipAddress={}", ipAddress);
        }

        // 对于通过多个代理的情况, 第一个IP为客户端真实IP,多个IP按照','分割 //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > MAX_IP_LENGTH) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


    /**
     * @Description: 从本地线程中获得事先存入的UserDto对象
     * @Author: hmr
     * @Date: 2019/12/20 19:30
     * @param
     * @reture: top.imuster.common.core.dto.UserDto
     **/
    // todo
    /*public static UserDto getLoginUser(String jwt) {
        Map<String, String> map = CookieUtil.readCookie(request, GlobalConstant.COOKIE_ACCESS_TOKEN_NAME);
        if(map!=null && map.get(GlobalConstant.COOKIE_ACCESS_TOKEN_NAME)!=null){
            String accessToken = map.get(GlobalConstant.COOKIE_ACCESS_TOKEN_NAME);
        }
        UserDto userDto = (UserDto) redisTemplate.opsForValue().get(RedisUtil.getAccessToken(accessToken));
        if(userDto == null){
            throw new RuntimeException("用户身份货过期,请重新登录后再操作");
        }

    }*/



}
