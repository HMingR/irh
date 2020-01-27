package top.imuster.user.provider.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.utils.JwtTokenUtil;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.user.api.dto.CheckValidDto;
import top.imuster.user.api.enums.CheckTypeEnum;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.provider.dao.ConsumerInfoDao;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.UserInfoService;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * UserInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("consumerInfoService")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Long> implements UserInfoService {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    private ConsumerInfoDao consumerInfoDao;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Override
    public BaseDao<UserInfo, Long> getDao() {
        return this.consumerInfoDao;
    }

    @Override
    public UserInfo loadUserDetailsByEmail(String email) {
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo = consumerInfoDao.selectEntryList(userInfo).get(0);
        if(userInfo == null) {
            throw new UserException("用户名或者密码错误");
        }
        if(userInfo.getState() == null || userInfo.getState() <= 20){
            throw new UserException("该账号已被冻结,请联系管理员");
        }
        return userInfo;
    }

    @Override
    public boolean checkValid(CheckValidDto checkValidDto) throws Exception{
        UserInfo condition = generateCheckCondition(checkValidDto);
        int i = consumerInfoDao.checkInfo(condition);
        return i == 0;
    }

    /**
     * @Author hmr
     * @Description 生成校验用户信息是否合法的参数
     * @Date: 2020/1/14 13:16
     * @param checkValidDto
     * @reture: top.imuster.user.api.pojo.UserInfo
     **/
    private UserInfo generateCheckCondition(CheckValidDto checkValidDto) throws InvocationTargetException, IllegalAccessException {
        CheckTypeEnum type = checkValidDto.getType();
        String validValue = checkValidDto.getValidValue();
        UserInfo condition = new UserInfo();
        String field = type.getType();
        BeanUtils.setProperty(condition, field, validValue);
        return condition;
    }

    @Override
    public void register(UserInfo userInfo, String code)throws Exception {
        UserInfo condition;
        CheckValidDto checkValidDto = new CheckValidDto();
        //校验邮箱
        String email = userInfo.getEmail();
        checkValidDto.setType(CheckTypeEnum.EMAIL);
        checkValidDto.setValidValue(email);
        condition = generateCheckCondition(checkValidDto);
        int i = consumerInfoDao.checkInfo(condition);
        //校验nickname
        String nickname = userInfo.getNickname();
        checkValidDto.setValidValue(nickname);
        checkValidDto.setType(CheckTypeEnum.NICKNAME);
        condition = generateCheckCondition(checkValidDto);
        i += consumerInfoDao.checkInfo(condition);
        if(i != 0){
            throw new UserException("邮箱或用户名重复");
        }
        String realToken = (String) redisTemplate.opsForValue().get(RedisUtil.getConsumerRegisterByEmailToken(email));
        if(StringUtils.isBlank(realToken) || !code.equalsIgnoreCase(realToken)){
            throw new UserException("验证码错误");
        }
        userInfo.setState(30);
        consumerInfoDao.insertEntry(userInfo);
    }

    @Override
    public void getCode(SendMessageDto sendMessageDto, String email, Integer type) throws JsonProcessingException {
        String code = UUID.randomUUID().toString().substring(0, 4);
        sendMessageDto.setUnit(TimeUnit.MINUTES);
        sendMessageDto.setExpiration(10L);
        sendMessageDto.setValue(code);
        sendMessageDto.setType(MqTypeEnum.EMAIL);
        if(type == 1){
            sendMessageDto.setRedisKey(RedisUtil.getConsumerRegisterByEmailToken(email));
            sendMessageDto.setTopic("注册");
            String body = new StringBuilder().append("欢迎注册,本次注册的验证码是").append(code).append(",该验证码有效期为10分钟").toString();
            sendMessageDto.setBody(body);
        }
        if(type == 2){
            sendMessageDto.setRedisKey(RedisUtil.getResetPwdByEmailToken(email));
            sendMessageDto.setTopic("修改密码");
            String body = new StringBuilder().append("该验证码用于重置密码:").append(code).append(",该验证码有效期为10分钟").toString();
            sendMessageDto.setBody(body);
        }
        generateSendMessageService.sendToMqAndReids(sendMessageDto);
    }

    @Override
    public String getEmailById(Long id) {
        return consumerInfoDao.selectEmailById(id);
    }
}