package top.imuster.common.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.exception.GlobalException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: BrowserTimesService
 * @Description: BrowserTimesService
 * @author: hmr
 * @date: 2020/2/15 17:02
 */
public abstract class BrowserTimesService {

    protected static final Logger log = LoggerFactory.getLogger(BrowserTimesService.class);

    @Autowired
    RedisTemplate redisTemplate;

    protected List<BrowserTimesDto> getBrowserTimesFromRedis(BrowserType type){
        if(type == null || redisTemplate == null){
            log.error("从redis中统计浏览记录的BrowserTimesService初始化失败, type为{},redisTemplate为{}", type, redisTemplate);
            throw new GlobalException("服务器内部错误");
        }
        List<BrowserTimesDto> list = new ArrayList<>();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(type.getRedisKeyHeader(), ScanOptions.NONE);
        while (cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();

            Long key = Long.parseLong((String) map.getKey());
            BrowserTimesDto dto = new BrowserTimesDto(key, Long.parseLong(String.valueOf(map.getValue())), type);
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(type.getRedisKeyHeader(), String.valueOf(key));
        }
        return list;
    }

    public abstract void generate();

}
