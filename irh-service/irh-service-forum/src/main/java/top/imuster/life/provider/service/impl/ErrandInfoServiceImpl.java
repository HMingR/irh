package top.imuster.life.provider.service.impl;


import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        page.setData(errandInfos);
        return Message.createBySuccess(page);
    }

    @Override
    public boolean isAvailable(Long id) {
        Integer state = errandInfoDao.selectStateById(id);
        if(state == null) return false;
        return state.intValue() == 2;
    }

    @Override
    public Message<String> deleteErrandById(Long id, Long userId) {
        ErrandInfo errandInfo = errandInfoDao.selectEntryList(id).get(0);
        if(errandInfo != null){
            Integer state = errandInfo.getState();
            if(state == 3) return Message.createByError("删除失败,该任务已被领取");
            if(!errandInfo.getPublisherId().equals(userId)){
                log.error("{}的用户恶意删除他人的跑腿信息", userId);
                return Message.createByError("不能删除其他人发布的跑腿,如果多次违规操作,将会冻结账号");
            }
            errandInfo.setState(1);
            errandInfoDao.updateByKey(errandInfo);
        }
        return Message.createByError("删除失败,请刷新后重试");
    }
}