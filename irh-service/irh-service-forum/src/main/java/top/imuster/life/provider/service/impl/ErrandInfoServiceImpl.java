package top.imuster.life.provider.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ErrandInfoDao errandInfoDao;

    @Override
    public BaseDao<ErrandInfo, Long> getDao() {
        return this.errandInfoDao;
    }

    @Override
    public Message<Page<ErrandInfo>> getListByCondition(Integer pageSize, Integer currentPage, Long userId) {
        Page<ErrandInfo> page = new Page<>();
        ErrandInfo condition = new ErrandInfo();
        condition.setPublisherId(userId);
        condition.setStartIndex((currentPage < 1 ? 1 : currentPage) * pageSize);
        condition.setEndIndex(pageSize);
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        List<ErrandInfo> errandInfos = errandInfoDao.selectList(condition);
        Integer count = errandInfoDao.selectListCountByUserId(userId);
        page.setTotalCount(count);
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