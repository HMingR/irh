package top.imuster.user.provider.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.user.api.dto.CheckValidDto;
import top.imuster.user.api.enums.CheckTypeEnum;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.provider.dao.UserInfoDao;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.UserInfoService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * UserInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("userInfoService")
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
    public Message<String> register(UserInfo userInfo){
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
        userInfo.setState(30);
        userInfoDao.insertEntry(userInfo);
        return Message.createBySuccess("注册成功，在个人中心的实名认证中提交相应材料并通过审核之后才能使用全部功能哦!");
    }

    @Override
    public String getEmailById(Long id) {
        return userInfoDao.selectEmailById(id);
    }

    @Override
    public void editUserRoleById(Long userId, String roleIds) {

    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "'user::' + #p0")
    public String getUserNameById(Long id) {
        String s = userInfoDao.selectUserNameById(id);
        if(StringUtils.isBlank(s)){
            s = String.valueOf(id);
        }
        return s;
    }

    @Override
    public long getUserTotalByCreateTime(String s) {
        return userInfoDao.selectUserTotalByCreateTime(s);
    }

    @Override
    public Long getIncrementUserByTime(String start, String end) {
        HashMap<String, String> params = new HashMap<>();
        params.put("startTime", start);
        params.put("endTime", end);
        return userInfoDao.selectIncrementUserByTime(params);
    }

    @Override
    public Message<UserDto> getUserDtoByUserId(Long userId) {
        UserDto userDto = userInfoDao.selectUserDtoById(userId);
        //没有找到,则直接返回用户id
        if(userDto == null) return Message.createBySuccess(new UserDto(userId));
        //此处把text设置成用户的昵称是为了适配前端
        return Message.createBySuccess(userDto.getNickname(), userDto);
    }

    @Override
    public Map<String, String> getAddAndPhoneById(Long userId) {
        return userInfoDao.selectAddAndPhoneById(userId);
    }

}