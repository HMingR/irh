package top.imuster.auth.component.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.imuster.auth.service.Impl.UsernameUserDetailsServiceImpl;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.utils.CookieUtil;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.security.api.bo.UserDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: IrhAuthenticationSuccessHandler
 * @Description: 认证成功的处理逻辑
 * @author: hmr
 * @date: 2020/4/30 20:14
 */
@Component
public class IrhAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(IrhAuthenticationSuccessHandler.class);

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UsernameUserDetailsServiceImpl usernameUserDetailsService;

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    @Value("${token.expireTime:86400}")
    private Integer tokenExpireTime;

    private void setTokenExpireTime(Integer tokenExpireTime){
        this.tokenExpireTime = tokenExpireTime;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("登录成功之后的处理");
        String clientId = "irhWebApp";
        String clientSecret = "$2a$10$IliTyZPWTbcMd3TI0pdLD.IrW843ZnfMV0tlgcSbbd4N7nqXjGMaW";

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if (null == clientDetails) {
            throw new UnapprovedClientAuthenticationException("clientId不存在" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配" + clientId);
        }

        //setTokenExpireTime(clientDetails.getAccessTokenValiditySeconds());

        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "client_credentials");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        String jti = (String) token.getAdditionalInformation().get("jti");

        Object principal = authentication.getPrincipal();
        UserDetails userDetails = null;
        if(principal instanceof UserDetails) userDetails = (UserDetails) principal;
        else{
            userDetails = usernameUserDetailsService.loadUserByUsername((String) principal);
        }
        saveToCookieAndRedis(jti, userDetails);
        response.setContentType("application/json;charset=UTF-8");

        Message<OAuth2AccessToken> successMsg = Message.createBySuccess(token);
        response.getWriter().write(objectMapper.writeValueAsString(successMsg));
    }

    private void saveToCookieAndRedis(String jti, UserDetails userDetails){
        saveAccessTokenToCookie(jti);

        UserDto userDto = userDetails.getUserInfo();
        try {
            redisTemplate.opsForValue().set(RedisUtil.getAccessToken(jti), objectMapper.writeValueAsString(userDto), tokenExpireTime.longValue(), TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    /**
     * @Author hmr
     * @Description 将用户申请到的令牌保存到cookie中
     * @Date: 2020/1/29 16:08
     * @param jti
     * @reture: void
     **/
    private void saveAccessTokenToCookie(String jti){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response, cookieDomain, "/", GlobalConstant.COOKIE_ACCESS_TOKEN_NAME, jti, cookieMaxAge, false);
    }
}
