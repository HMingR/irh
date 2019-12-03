package top.imuster.user.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.service.base.BaseServiceImpl;
import top.imuster.user.dao.AuthInfoDao;
import top.imuster.user.pojo.AuthInfo;
import top.imuster.user.service.AuthInfoService;

import javax.annotation.Resource;

/**
 * AuthInfoService 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Service("authInfoService")
public class AuthInfoServiceImpl extends BaseServiceImpl<AuthInfo, Long> implements AuthInfoService {

    @Resource
    private AuthInfoDao authInfoDao;

    @Override
    public BaseDao<AuthInfo, Long> getDao() {
        return this.authInfoDao;
    }
}