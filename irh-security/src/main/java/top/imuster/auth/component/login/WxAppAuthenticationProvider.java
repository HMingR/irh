package top.imuster.auth.component.login;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.client.RestTemplate;
import top.imuster.auth.config.WxAppAuthenticationToken;
import top.imuster.auth.service.RoleInfoService;
import top.imuster.auth.service.WxAppLoginService;
import top.imuster.common.core.dto.UserDto;
import top.imuster.security.api.bo.UserDetails;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: WxAppAuthenticationProvider
 * @Description: WxAppAuthenticationProvider
 * @author: hmr
 * @date: 2020/5/19 14:31
 */
public class WxAppAuthenticationProvider implements AuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${wxApp.appId}")
    private String appId;

    @Value("${wxApp.appSecret}")
    private String appSecret;

    //请求微信的url
    @Value("${wxApp.UrlPrefix}")
    private String wxAppPrefix;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @Resource
    WxAppLoginService wxAppLoginService;

    @Resource
    RoleInfoService roleInfoService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WxAppAuthenticationToken appAuthenticationToken = (WxAppAuthenticationToken) authentication;
        String credentials = String.valueOf(appAuthenticationToken.getCredentials());
        String url = new StringBuffer(wxAppPrefix).append("appid=").append(appId).append("&secret=").append(appSecret).append("&js_code=").append(credentials).append("&grant_type=authorization_code").toString();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if(responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK)
        {
            String sessionData = responseEntity.getBody();
            JSONObject jsonObject = JSONObject.parseObject(sessionData);

            /*if(StringUtils.isNotBlank(jsonObject.get("errcode").toString())) {
                log.error("------->微信登录请求openId失败,错误信息为{}", responseEntity);
                throw new AuthenticationServiceException("认证失败,请刷新后重试");
            }*/
            String openid = jsonObject.getString("openid");

            //获取会话秘钥
            if(StringUtils.isBlank(openid)) throw new AuthenticationServiceException("认证失败,请刷新后重试");

            UserInfo userInfo = wxAppLoginService.loginByOpenId(openid);

            if(userInfo == null) throw new AuthenticationServiceException("该微信没有绑定irh平台账号,请先注册再使用微信登录");

            String userAuth = "";
            List<String> roleName = roleInfoService.getRoleNameByUserName(userInfo.getEmail());
            userAuth  = StringUtils.join(roleName.toArray(), ",");

            //已经认证过的需要加一个角色名称
            /*if(userInfo.getState() == 40){
                if(StringUtils.isNotBlank(userAuth)) userAuth += ",identified";
                else userAuth = "identified";
            }*/
            UserDto userDto = new UserDto(userInfo.getId(), userInfo.getEmail(), userInfo.getNickname(), userInfo.getPortrait(), userInfo.getType());
            UserDetails userDetails = new UserDetails(userInfo.getEmail(), userInfo.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(userAuth));
            userDetails.setUserInfo(userDto);
            return new WxAppAuthenticationToken(userDetails, credentials);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (WxAppAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
