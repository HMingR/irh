package top.imuster.auth.service.Impl;


import org.springframework.stereotype.Service;
import top.imuster.auth.dao.UserAuthenInfoDao;
import top.imuster.auth.service.UserAuthenInfoService;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.security.api.pojo.UserAuthenInfo;

import javax.annotation.Resource;

/**
 * UserAuthenInfoService 实现类
 * @author 黄明人
 * @since 2020-05-30 17:35:12
 */
@Service("userAuthenInfoService")
public class UserAuthenInfoServiceImpl extends BaseServiceImpl<UserAuthenInfo, Long> implements UserAuthenInfoService {

    @Resource
    private UserAuthenInfoDao userAuthenInfoDao;

    @Override
    public BaseDao<UserAuthenInfo, Long> getDao() {
        return this.userAuthenInfoDao;
    }
}