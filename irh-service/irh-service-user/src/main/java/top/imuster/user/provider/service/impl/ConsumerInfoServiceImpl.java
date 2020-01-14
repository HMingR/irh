package top.imuster.user.provider.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.utils.JwtTokenUtil;
import top.imuster.common.core.annotation.MqGenerate;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.user.api.bo.ConsumerDetails;
import top.imuster.user.api.dto.CheckValidDto;
import top.imuster.user.api.enums.CheckTypeEnum;
import top.imuster.user.api.pojo.ConsumerInfo;
import top.imuster.user.provider.dao.ConsumerInfoDao;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.ConsumerInfoService;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ConsumerInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("consumerInfoService")
public class ConsumerInfoServiceImpl extends BaseServiceImpl<ConsumerInfo, Long> implements ConsumerInfoService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private ConsumerInfoDao consumerInfoDao;

    @Override
    public BaseDao<ConsumerInfo, Long> getDao() {
        return this.consumerInfoDao;
    }

    @Override
    public String login(String email, String password) {
        try{
            ConsumerInfo condition = new ConsumerInfo();
            condition.setEmail(email);
            ConsumerInfo consumerInfo = consumerInfoDao.selectEntryList(condition).get(0);
            boolean matches = passwordEncoder.matches(password, consumerInfo.getPassword());
            if(!matches){
                throw new UsernameNotFoundException("用户名或者密码错误");
            }
            if(StringUtils.isEmpty(String.valueOf(consumerInfo.getState())) || consumerInfo.getState() <= 20){
                throw new UserException("用户信息异常或用户被锁定");
            }
            String token = JwtTokenUtil.generateToken(consumerInfo.getEmail(), consumerInfo.getId());
            //将用户的基本信息存入redis中，并设置过期时间
            redisTemplate.opsForValue()
                    .set(RedisUtil.getAccessToken(token),
                            new UserDto(consumerInfo.getId(),
                                    consumerInfo.getEmail(),
                                    GlobalConstant.userType.MANAGEMENT.getName(),
                                    GlobalConstant.userType.MANAGEMENT.getType()),
                            GlobalConstant.REDIS_JWT_EXPIRATION, TimeUnit.SECONDS);
            return token;
        }catch (Exception e){
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public ConsumerDetails loadConsumerDetailsByName(String userName) {
        ConsumerInfo consumerInfo = new ConsumerInfo();
        consumerInfo.setEmail(userName);
        consumerInfo = consumerInfoDao.selectEntryList(consumerInfo).get(0);
        if(consumerInfo == null) {
            throw new UsernameNotFoundException("用户名或者密码错误");
        }
        if(consumerInfo.getState() == null || consumerInfo.getState() <= 20){
            throw new RuntimeException("该账号已被冻结,请联系管理员");
        }
        return new ConsumerDetails(consumerInfo);
    }

    @Override
    public boolean checkValid(CheckValidDto checkValidDto) throws Exception{
            ConsumerInfo condition = generateCheckCondition(checkValidDto);
            int i = consumerInfoDao.checkInfo(condition);
            return i == 0;
    }

    /**
     * @Author hmr
     * @Description 生成校验用户信息是否合法的参数
     * @Date: 2020/1/14 13:16
     * @param checkValidDto
     * @reture: top.imuster.user.api.pojo.ConsumerInfo
     **/
    private ConsumerInfo generateCheckCondition(CheckValidDto checkValidDto) throws InvocationTargetException, IllegalAccessException {
        CheckTypeEnum type = checkValidDto.getType();
        String validValue = checkValidDto.getValidValue();
        ConsumerInfo condition = new ConsumerInfo();
        String field = type.getType();
        BeanUtils.setProperty(condition, field, validValue);
        return condition;
    }

    @Override
    @MqGenerate(isSaveToRedis = true)
    public void resetPwdByEmail(SendMessageDto sendMessageDto, String email) throws JsonProcessingException {
        String msg = UUID.randomUUID().toString().substring(0, 4);
        sendMessageDto.setSourceId(-1L);
        sendMessageDto.setBody("该验证码用于重置密码," + msg + ",有效期为10分钟");
        sendMessageDto.setTopic("修改密码");
        sendMessageDto.setSourceType(30);
        sendMessageDto.setType("EMAIL");
        sendMessageDto.setUnit(TimeUnit.MINUTES);
        sendMessageDto.setExpiration(10L);
        sendMessageDto.setRedisKey(RedisUtil.getResetPwdByEmailToken(email));
        sendMessageDto.setValue(msg);
        LOGGER.info("重置密码准备发送邮箱的实体类{}",sendMessageDto);
    }

    @Override
    public void register(ConsumerInfo consumerInfo, String code)throws Exception {
        ConsumerInfo condition;
        CheckValidDto checkValidDto = new CheckValidDto();
        //校验邮箱
        String email = consumerInfo.getEmail();
        checkValidDto.setType(CheckTypeEnum.EMAIL);
        checkValidDto.setValidValue(email);
        condition = generateCheckCondition(checkValidDto);
        int i = consumerInfoDao.checkInfo(condition);
        //校验nickname
        String nickname = consumerInfo.getNickname();
        checkValidDto.setValidValue(nickname);
        checkValidDto.setType(CheckTypeEnum.NICKNAME);
        condition = generateCheckCondition(checkValidDto);
        i += consumerInfoDao.checkInfo(condition);
        if(i != 0){
            throw new UserException("邮箱或用户名重复");
        }
        String realToken = (String) redisTemplate.opsForValue().get(RedisUtil.getConsumerRegisterByEmailToken(email));
        if(realToken.isEmpty() || !code.equalsIgnoreCase(realToken)){
            throw new UserException("验证码错误");
        }
        consumerInfo.setState(30);
    }

    @Override
    @MqGenerate(isSaveToRedis = true)
    public void getCode(SendMessageDto sendMessageDto, String email) throws JsonProcessingException {
        String code = UUID.randomUUID().toString().substring(0, 4);
        sendMessageDto.setUnit(TimeUnit.MINUTES);
        sendMessageDto.setExpiration(10L);
        sendMessageDto.setRedisKey(RedisUtil.getConsumerRegisterByEmailToken("1978773465@qq.com"));
        sendMessageDto.setValue(code);
        sendMessageDto.setTopic("注册");
        sendMessageDto.setType("EMAIL");
        sendMessageDto.setBody("欢迎注册,本次注册的验证码是" + code + ",该验证码有效期为10分钟");
    }
}