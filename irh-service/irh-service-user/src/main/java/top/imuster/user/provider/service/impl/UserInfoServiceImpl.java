package top.imuster.user.provider.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.user.api.dto.CheckValidDto;
import top.imuster.user.api.enums.CheckTypeEnum;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.provider.dao.UserInfoDao;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.UserInfoService;

import javax.annotation.Resource;

/**
 * UserInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("userInfoService")
@CacheConfig(cacheNames = "user")
@Slf4j
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Long> implements UserInfoService {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    private UserInfoDao userInfoDao;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Override
    public BaseDao<UserInfo, Long> getDao() {
        return this.userInfoDao;
    }

    @Override
    public UserInfo loadUserDetailsByEmail(String email) {
        UserInfo condition = new UserInfo();
        condition.setEmail(email);
        UserInfo userInfo = userInfoDao.selectUserRoleByCondition(condition);
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
        int i = userInfoDao.checkInfo(condition);
        return i == 0;
    }

    /**
     * @Author hmr
     * @Description 生成校验用户信息是否合法的参数
     * @Date: 2020/1/14 13:16
     * @param checkValidDto
     * @reture: top.imuster.user.api.pojo.UserInfo
     **/
    private UserInfo generateCheckCondition(CheckValidDto checkValidDto){
        try{
            CheckTypeEnum type = checkValidDto.getType();
            String validValue = checkValidDto.getValidValue();
            UserInfo condition = new UserInfo();
            String field = type.getType();
            BeanUtils.setProperty(condition, field, validValue);
            return condition;
        }catch (Exception e){
            log.error("生成校验用户信息是否合法的参数是出现错误{}",e.getMessage(), e);
            throw new UserException("服务器繁忙,请稍后重试");
        }
    }

    @Override
    public Message<String> register(UserInfo userInfo, String code){
        UserInfo condition;
        CheckValidDto checkValidDto = new CheckValidDto();
        //校验邮箱
        String email = userInfo.getEmail();
        checkValidDto.setType(CheckTypeEnum.EMAIL);
        checkValidDto.setValidValue(email);
        condition = generateCheckCondition(checkValidDto);
        int i = userInfoDao.checkInfo(condition);
        //校验nickname
        String nickname = userInfo.getNickname();
        checkValidDto.setValidValue(nickname);
        checkValidDto.setType(CheckTypeEnum.NICKNAME);
        condition = generateCheckCondition(checkValidDto);
        i += userInfoDao.checkInfo(condition);
        if(i != 0){
            return Message.createByError("邮箱或用户名重复");
        }
        String realToken = (String) redisTemplate.opsForValue().get(RedisUtil.getConsumerRegisterByEmailToken(email));
        if(StringUtils.isBlank(realToken) || !code.equalsIgnoreCase(realToken)){
            return Message.createByError("验证码错误");
        }
        userInfo.setState(30);
        int result = userInfoDao.insertEntry(userInfo);
        if(result != 1){
            log.error("用户注册失败,校验参数没有问题,但是在最后存入数据库的时候出现问题,用户注册信息为{}", userInfo);
            return Message.createByError("服务器内部错误,请稍后重试");
        }
        return Message.createBySuccess("注册成功");
    }

    @Override
    public String getEmailById(Long id) {
        return userInfoDao.selectEmailById(id);
    }

    @Override
    public void editUserRoleById(Long userId, String roleIds) {

    }

    @Override
    @Cacheable(key = "#p0")
    public String getUserNameById(Long id) {
        return userInfoDao.selectUserNameById(id);
    }
}