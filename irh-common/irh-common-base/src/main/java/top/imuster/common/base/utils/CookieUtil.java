package top.imuster.common.base.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: CookieUtil
 * @Description: CookieUtil
 * @author: hmr
 * @date: 2020/1/29 16:00
 */
public class CookieUtil {

    private CookieUtil(){

    }

    /**
     * @Author hmr
     * @Description 给response添加cookie
     * @Date: 2020/1/29 16:01
     * @param response
     * @param domain
     * @param path
     * @param name
     * @param value
     * @param maxAge
     * @param httpOnly
     * @reture: void
     **/
    public static void addCookie(HttpServletResponse response, String domain, String path, String name,
                                 String value, int maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        response.addCookie(cookie);
    }


    /**
     * @Author hmr
     * @Description 从cookie中根据名字读取相应cookie
     * @Date: 2020/1/29 16:02
     * @param request
     * @param cookieNames
     * @reture: java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String,String> readCookie(HttpServletRequest request, String ... cookieNames) {
        Map<String,String> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                String cookieValue = cookie.getValue();
                for(int i=0;i<cookieNames.length;i++){
                    if(cookieNames[i].equals(cookieName)){
                        cookieMap.put(cookieName,cookieValue);
                    }
                }
            }
        }
        return cookieMap;

    }

}
