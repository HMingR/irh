package top.imuster.auth.service.Impl;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import top.imuster.auth.dao.UserLoginInfoDao;
import top.imuster.auth.service.UserLoginInfoService;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.security.api.pojo.UserLoginInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserLoginInfoService 实现类
 * @author 黄明人
 * @since 2020-05-26 09:40:11
 */
@Service("userLoginInfoService")
public class UserLoginInfoServiceImpl extends BaseServiceImpl<UserLoginInfo, Long> implements UserLoginInfoService {

    @Resource
    private UserLoginInfoDao userLoginInfoDao;

    @Override
    public BaseDao<UserLoginInfo, Long> getDao() {
        return this.userLoginInfoDao;
    }

    @Override
    public UserLoginInfo getInfoByLoginName(String loginName) {
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setLoginName(loginName);
        List<UserLoginInfo> userLoginInfos = userLoginInfoDao.selectEntryList(userLoginInfo);
        if(CollectionUtils.isNotEmpty(userLoginInfos)){
            return userLoginInfos.get(0);
        }
        return null;
    }
}