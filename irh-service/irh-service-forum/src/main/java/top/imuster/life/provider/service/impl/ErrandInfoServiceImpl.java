package top.imuster.life.provider.service.impl;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ErrandInfo;
import top.imuster.life.provider.dao.ErrandInfoDao;
import top.imuster.life.provider.service.ErrandInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ErrandInfoService 实现类
 * @author 黄明人
 * @since 2020-02-11 17:49:35
 */
@Service("errandInfoService")
public class ErrandInfoServiceImpl extends BaseServiceImpl<ErrandInfo, Long> implements ErrandInfoService {

    @Resource
    private ErrandInfoDao errandInfoDao;

    @Override
    public BaseDao<ErrandInfo, Long> getDao() {
        return this.errandInfoDao;
    }

    @Override
    public Message<Page<ErrandInfo>> getListByCondition(Page<ErrandInfo> page, Long userId) {
        if (null == page.getSearchCondition()){
            page.setSearchCondition(new ErrandInfo());
        }
        if(StringUtils.isBlank(page.getSearchCondition().getOrderField())){
            page.getSearchCondition().setOrderField("create_time");
            page.getSearchCondition().setOrderFieldType("DESC");
        }
        List<ErrandInfo> errandInfos = errandInfoDao.selectList(page.getSearchCondition());
        page.setTotalCount(errandInfoDao.selectEntryListCount(page.getSearchCondition()));
        page.setResult(errandInfos);
        return Message.createBySuccess(page);
    }

    @Override
    public boolean isAvailable(Long id) {
        Integer state = errandInfoDao.selectStateById(id);
        if(state == null) return false;
        return state.intValue() == 2;
    }
}