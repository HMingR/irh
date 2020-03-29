package top.imuster.user.provider.service.impl;


import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.InterestTagInfo;
import top.imuster.user.api.pojo.UserInterestTagRel;
import top.imuster.user.provider.dao.InterestTagInfoDao;
import top.imuster.user.provider.service.InterestTagInfoService;
import top.imuster.user.provider.service.UserInterestTagRelService;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * InterestTagInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("interestTagInfoService")
@CacheConfig(cacheNames = "interest")
public class InterestTagInfoServiceImpl extends BaseServiceImpl<InterestTagInfo, Long> implements InterestTagInfoService {

    @Resource
    private InterestTagInfoDao interestTagInfoDao;

    @Resource
    private UserInterestTagRelService userInterestTagRelService;

    @Override
    public BaseDao<InterestTagInfo, Long> getDao() {
        return this.interestTagInfoDao;
    }

    @Override
    public Page<InterestTagInfo> list(Page<InterestTagInfo> page) {
        InterestTagInfo condition = page.getSearchCondition();
        condition.setState(2);
        Page<InterestTagInfo> interestTagInfoPage = this.selectPage(condition, page);
        List<InterestTagInfo> interestTagInfos = interestTagInfoPage.getData();
        interestTagInfos.stream().forEach(interestTagInfo -> {
            Long count = userInterestTagRelService.getTagCountByTagId(interestTagInfo.getId());
            interestTagInfo.setTotal(count);
        });
        List<InterestTagInfo> collect = interestTagInfos.stream().sorted(Comparator.comparingLong(InterestTagInfo::getTotal).reversed()).collect(Collectors.toList());
        page.setData(collect);
        return page;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteById(Long id) {
        InterestTagInfo condition = new InterestTagInfo();
        condition.setId(id);
        condition.setState(1);
        interestTagInfoDao.updateByKey(condition);

        UserInterestTagRel userInterestTagRel = new UserInterestTagRel();
        userInterestTagRel.setTagId(id);
        userInterestTagRelService.deleteByCondtion(userInterestTagRel);
    }

    @Override
    @Cacheable(key = "#p0")
    public String getTagNameById(Long id) {
        return interestTagInfoDao.selectTagNameById(id);
    }

    @Override
    public Message<List<Long>> getUserTagByUserId(Long userId) {
        List<Long> tagIds = userInterestTagRelService.getUserTagByUserId(userId);
        return Message.createBySuccess(tagIds);
    }
}