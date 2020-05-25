package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.PropagateInfo;
import top.imuster.user.provider.dao.PropagateInfoDao;
import top.imuster.user.provider.service.PropagateInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * PropagateInfoService 实现类
 * @author 黄明人
 * @since 2020-05-16 10:05:59
 */
@Service("propagateInfoService")
public class PropagateInfoServiceImpl extends BaseServiceImpl<PropagateInfo, Long> implements PropagateInfoService {

    @Resource
    private PropagateInfoDao propagateInfoDao;

    @Override
    public BaseDao<PropagateInfo, Long> getDao() {
        return this.propagateInfoDao;
    }

    @Override
    public Message<String> release(PropagateInfo propagateInfo) {
        propagateInfoDao.insertEntry(propagateInfo);
        propagateInfo.getId();
        return null;
    }

    @Override
    public Message<Page<PropagateInfo>> getBriefList(Integer pageSize, Integer currentPage, Integer type) {
        Page<PropagateInfo> page = new Page<>();
        PropagateInfo condition = new PropagateInfo();
        condition.setState(2);
        condition.setType(type);
        condition.setStartIndex((currentPage - 1) * pageSize);
        condition.setEndIndex(pageSize);
        condition.setOrderField("create_times");
        condition.setOrderFieldType("DESC");
        List<PropagateInfo> list = propagateInfoDao.selectBriefInfoList(condition);
        Integer count = this.selectEntryListCount(condition);
        page.setData(list);
        page.setTotalCount(count);
        return Message.createBySuccess(page);
    }
}