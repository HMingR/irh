package top.imuster.auth.service.Impl;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.imuster.auth.dao.WxAppLoginDao;
import top.imuster.auth.service.WxAppLoginService;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.dto.rabbitMq.SendEmailDto;
import top.imuster.common.core.enums.TemplateEnum;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.security.api.pojo.WxAppLoginInfo;
import top.imuster.user.api.dto.BindWxDto;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * WxAppLoginService 实现类
 * @author 黄明人
 * @since 2020-05-19 15:28:28
 */
@Service("wxAppLoginService")
public class WxAppLoginServiceImpl extends BaseServiceImpl<WxAppLoginInfo, Long> implements WxAppLoginService {



    @Value("${wxApp.appId}")
    private String appId;

    @Value("${wxApp.appSecret}")
    private String appSecret;

    //请求微信的url
    @Value("${wxApp.UrlPrefix}")
    private String wxAppPrefix;

    @Resource
    private WxAppLoginDao wxAppLoginDao;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @Override
    public BaseDao<WxAppLoginInfo, Long> getDao() {
        return this.wxAppLoginDao;
    }

    @Override
    public UserInfo loginByOpenId(String openId) {
        Long userId = wxAppLoginDao.selectUserIdByOpenId(openId);
        if(userId == null) return null;
        UserInfo userInfo = userServiceFeignApi.getInfoById(userId);
        return userInfo;
    }

    @Override
    public Message<String> binding(BindWxDto bindDto) {
        String redisCode = (String) redisTemplate.opsForValue().get(RedisUtil.getUserBindWxEmailCode(bindDto.getUserId()));
        if(StringUtils.isBlank(redisCode) || !bindDto.getBindEmailCode().equalsIgnoreCase(redisCode)){
            return Message.createByError("验证码失效或错误");
        }

        WxAppLoginInfo condition = new WxAppLoginInfo();
        condition.setState(2);
        condition.setUserId(bindDto.getUserId());
        Integer count = wxAppLoginDao.selectEntryListCount(condition);
        if(count != 0) return Message.createBySuccess("已经绑定了微信,请不要重复绑定");

        WxAppLoginInfo wxAppLoginInfo = new WxAppLoginInfo();
        String url = new StringBuffer(wxAppPrefix).append("appid=").append(appId).append("&secret=").append(appSecret).append("&js_code=").append(bindDto.getCode()).append("&grant_type=authorization_code").toString();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        if(responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK)
        {
            String sessionData = responseEntity.getBody();
            JSONObject jsonObject = JSONObject.parseObject(sessionData);

            String openid = jsonObject.getString("openid");
            String session_key = jsonObject.getString("session_key");

            if(StringUtils.isBlank(openid) || StringUtils.isBlank(session_key)) return Message.createByError("请重新刷新页面");

            wxAppLoginInfo.setOpenId(openid);
            wxAppLoginInfo.setSessionKey(session_key);
            wxAppLoginInfo.setUserId(bindDto.getUserId());
        }else{
            return Message.createByError("绑定失败,请稍后重试");
        }
        wxAppLoginDao.insertEntry(wxAppLoginInfo);
        return Message.createBySuccess();
    }

    @Override
    public Message<String> sendBindingEmail(UserDto userDto) {
        String email = userDto.getLoginName();
        Long userId = userDto.getUserId();
        String code = UUID.randomUUID().toString().substring(0, 4);
        log.debug("微信绑定验证码为{}", code);
        redisTemplate.opsForValue().set(RedisUtil.getUserBindWxEmailCode(userId), code, 10, TimeUnit.MINUTES);
        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setEmail(email);
        sendEmailDto.setSubject("irh平台绑定微信验证码");
        sendEmailDto.setContent(code);
        sendEmailDto.setTemplateEnum(TemplateEnum.SIMPLE_TEMPLATE);
        generateSendMessageService.sendToMq(sendEmailDto);

        return Message.createBySuccess();
    }

    @Override
    public Message<Integer> checkIsBind(Long userId) {
        WxAppLoginInfo condition = new WxAppLoginInfo();
        condition.setState(2);
        condition.setUserId(userId);
        Integer count = wxAppLoginDao.selectEntryListCount(condition);
        return Message.createBySuccess(count);
    }

}