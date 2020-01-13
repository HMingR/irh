package top.imuster.user.provider.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.annotation.MqGenerate;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.base.utils.JwtTokenUtil;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.user.api.bo.ConsumerDetails;
import top.imuster.user.api.pojo.ConsumerInfo;
import top.imuster.user.provider.dao.ConsumerInfoDao;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.ConsumerInfoService;

import javax.annotation.Resource;
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
    public boolean checkValid(ConsumerInfo consumerInfo) {
        int i = consumerInfoDao.checkInfo(consumerInfo);
        return i == 0;
    }

    @Override
    public void resetPwdByEmail(String email) throws JsonProcessingException {
        LOGGER.info("发送邮箱");
        String msg = UUID.randomUUID().toString().substring(0, 4);
        SendMessageDto sendMessageDto = new SendMessageDto();
        sendMessageDto.setSourceId(-1L);
        sendMessageDto.setBody(msg);
        sendMessageDto.setTopic("修改密码的验证码");
        sendMessageDto.setSourceType(30);
        sendMessageDto.setType("EMAIL");
        sendMessageDto.setUnit(TimeUnit.MINUTES);
        sendMessageDto.setRedisKey(RedisUtil.getResetPwdByEmailToken(email));
        sendMessageDto.setRedisValue(msg);
        generate(sendMessageDto);
    }

    @MqGenerate(isSaveToRedis = true)
    private void generate(SendMessageDto sendMessageDto) throws JsonProcessingException {
        LOGGER.info("发送邮箱,发送的内容为{}", new ObjectMapper().writeValueAsString(sendMessageDto));
    }

}