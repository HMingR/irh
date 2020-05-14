package top.imuster.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.goods.api.dto.GoodsForwardDto;
import top.imuster.goods.service.RedisOperateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RedisOperateService
 * @Description: RedisOperateService
 * @author: hmr
 * @date: 2020/5/9 9:16
 */
@Service("redisOperateService")
public class RedisOperateServiceImpl implements RedisOperateService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<GoodsForwardDto> transCollect2DB(Integer type) {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisUtil.getGoodsCollectMapKey(type), ScanOptions.NONE);
        List<GoodsForwardDto> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            Long key = Long.parseLong(String.valueOf(map.getKey()));
            GoodsForwardDto dto = new GoodsForwardDto(key, Integer.parseInt(String.valueOf(map.getValue())));
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(GlobalConstant.IRH_FORUM_FORWARD_TIMES_MAP, String.valueOf(key));
        }
        return list;
    }
}
