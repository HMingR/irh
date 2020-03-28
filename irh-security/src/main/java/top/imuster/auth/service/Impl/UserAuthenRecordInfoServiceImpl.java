package top.imuster.auth.service.Impl;


import org.springframework.stereotype.Service;
import top.imuster.auth.dao.UserAuthenRecordInfoDao;
import top.imuster.auth.service.UserAuthenRecordInfoService;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.security.pojo.UserAuthenRecordInfo;

import javax.annotation.Resource;

/**
 * UserAuthenRecordInfoService 实现类
 * @author 黄明人
 * @since 2020-03-27 15:53:30
 */
@Service("userAuthenRecordInfoService")
public class UserAuthenRecordInfoServiceImpl extends BaseServiceImpl<UserAuthenRecordInfo, Long> implements UserAuthenRecordInfoService {

    @Resource
    private UserAuthenRecordInfoDao userAuthenRecordInfoDao;

    @Override
    public BaseDao<UserAuthenRecordInfo, Long> getDao() {
        return this.userAuthenRecordInfoDao;
    }
}