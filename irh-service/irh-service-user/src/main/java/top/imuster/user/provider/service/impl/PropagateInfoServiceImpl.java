package top.imuster.user.provider.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.user.api.pojo.PropagateInfo;
import top.imuster.user.provider.dao.PropagateInfoDao;
import top.imuster.user.provider.service.PropagateInfoService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PropagateInfoService 实现类
 * @author 黄明人
 * @since 2020-05-16 10:05:59
 */
@Service("propagateInfoService")
public class PropagateInfoServiceImpl extends BaseServiceImpl<PropagateInfo, Long> implements PropagateInfoService {

    @Resource
    private PropagateInfoDao propagateInfoDao;

    @Autowired
    RedisTemplate redisTemplate;

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

    @Override
    public void trans2DB() {
        Map<Long, Integer> mapFromRedis = getMapFromRedis();
        if(mapFromRedis == null || mapFromRedis.isEmpty()) return;
        Integer total = propagateInfoDao.updateBrowseTimesByMap(mapFromRedis);
        log.info("-------->一共更新了{}个广告或通知的浏览次数", total);
    }

    /**
     * @Author hmr
     * @Description 从redis中获得propagate的浏览次数
     * @Date: 2020/5/31 15:13
     * @param
     * @reture: java.util.Map<java.lang.Long,java.lang.Integer> key为propagate的targetId，value为total
     **/
    private Map<Long, Integer> getMapFromRedis(){
        Cursor<Map.Entry<Object, Object>> res = redisTemplate.opsForHash().scan(BrowserType.PROPAGATE.getRedisKeyHeader(), ScanOptions.NONE);
        HashMap<Long, Integer> resMap = new HashMap<>();
        while(res.hasNext()){
            Map.Entry<Object, Object> next = res.next();
            if(next.getKey() == null || next.getValue() == null) continue;
            String key = String.valueOf(next.getKey());
            if(StringUtils.isEmpty(key)) continue;
            long targetId = Long.parseLong(key);
            String value = String.valueOf(next.getValue());
            if(StringUtils.isEmpty(value)) continue;
            int total = Integer.parseInt(value);
            resMap.put(targetId, total);
            redisTemplate.opsForHash().delete(BrowserType.PROPAGATE.getRedisKeyHeader(), key);
        }
        return resMap;
    }

}