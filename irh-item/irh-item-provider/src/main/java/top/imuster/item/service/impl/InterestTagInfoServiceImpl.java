package top.imuster.item.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.item.dao.InterestTagInfoDao;
import top.imuster.item.pojo.InterestTagInfo;
import top.imuster.item.service.InterestTagInfoService;
import top.imuster.service.base.BaseServiceImpl;

import javax.annotation.Resource;

/**
 * InterestTagInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("interestTagInfoService")
public class InterestTagInfoServiceImpl extends BaseServiceImpl<InterestTagInfo, Long> implements InterestTagInfoService {

    @Resource
    private InterestTagInfoDao interestTagInfoDao;

    @Override
    public BaseDao<InterestTagInfo, Long> getDao() {
        return this.interestTagInfoDao;
    }
}