package top.imuster.user.provider.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @ClassName: IrhAuthenticationEntryPoint
 * @Description: IrhAuthenticationEntryPoint
 * @author: hmr
 * @date: 2020/5/19 9:44
 */
@Component
public class IrhAuthenticationEntryPoint  extends OAuth2AuthenticationEntryPoint {

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Autowired
    BaseOAuth2ProtectedResourceDetails baseOAuth2ProtectedResourceDetails;

    private WebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();

    @Resource
    RestTemplate restTemplate;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        try {
            //解析异常，如果是401则处理
            ResponseEntity<?> result = exceptionTranslator.translate(authException);
            if (result.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
                formData.add("client_id", "irhWebApp");
                formData.add("client_secret", oAuth2ClientProperties.getClientSecret());
                formData.add("grant_type", "refresh_token");
                String authorization = request.getHeader("Authorization");
                String jwtString = StringUtils.substringAfter("bearer", authorization);
                formData.add("refresh_token", jwtString);
//                Cookie[] cookie=request.getCookies();
//                for(Cookie coo:cookie){
//                    if(coo.getName().equals("refresh_token")){
//                    }
//                }
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                Map map = restTemplate.exchange(baseOAuth2ProtectedResourceDetails.getAccessTokenUri(), HttpMethod.POST,
                        new HttpEntity<MultiValueMap<String, String>>(formData, headers), Map.class).getBody();
                //如果刷新异常,则坐进一步处理
                if(map.get("error")!=null){
                    // 返回指定格式的错误信息
                    response.setStatus(401);
                    response.setHeader("Content-Type", "application/json;charset=utf-8");
                    response.getWriter().print("{\"code\":1,\"message\":\""+map.get("error_description")+"\"}");
                    response.getWriter().flush();
                    //如果是网页,跳转到登陆页面
                    //response.sendRedirect("login");
                }else{
                    //如果刷新成功则存储cookie并且跳转到原来需要访问的页面
                    for(Object key:map.keySet()){
                        response.addCookie(new Cookie(key.toString(),map.get(key).toString()));
                    }
                    request.getRequestDispatcher(request.getRequestURI()).forward(request,response);
                }
            }else{
                //如果不是401异常，则以默认的方法继续处理其他异常
                super.commence(request,response,authException);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
