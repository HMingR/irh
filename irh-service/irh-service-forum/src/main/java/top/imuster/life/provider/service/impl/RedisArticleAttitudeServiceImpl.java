package top.imuster.life.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.forum.api.dto.UpCountDto;
import top.imuster.forum.api.dto.UpDto;
import top.imuster.forum.api.enums.UpStateEnum;
import top.imuster.life.provider.service.RedisArticleAttitudeService;

import java.util.*;

/**
 * @ClassName: RedisArticleAttitudeServiceImpl
 * @Description: RedisArticleAttitudeServiceImpl
 * @author: hmr
 * @date: 2020/2/8 17:26
 */
@Service("redisArticleAttitudeService")
public class RedisArticleAttitudeServiceImpl implements RedisArticleAttitudeService {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void saveUp2Redis(Long targetId, Integer type, Long userId) {
        String key = RedisUtil.getUpKey(targetId, type, userId);
        redisTemplate.opsForHash().put(GlobalConstant.IRH_USER_UP_MAP, key, UpStateEnum.UP.getType());
    }

    @Override
    public void downFromRedis(Long targetId, Integer type, Long userId) {
        String key = RedisUtil.getUpKey(targetId, type, userId);
        redisTemplate.opsForHash().put(GlobalConstant.IRH_USER_UP_MAP, key, UpStateEnum.NONE.getType());
    }

    @Override
    public void incrementUpCount(Long targetId, Integer type) {
        String key = new StringBuffer().append(targetId).append("::").append(type).toString();
        redisTemplate.opsForHash().increment(GlobalConstant.IRH_USER_UP_TOTAL, key, 1);
    }

    @Override
    public void decrementUpCount(Long targetId, Integer type) {
        String key = new StringBuffer().append(targetId).append("::").append(type).toString();
        redisTemplate.opsForHash().increment(GlobalConstant.IRH_USER_UP_TOTAL, key, -1);
    }

    @Override
    public List<UpDto> getAllUpFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(GlobalConstant.IRH_USER_UP_MAP, ScanOptions.NONE);
        List<UpDto> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();

            String[] split = key.split("::");
            Long targetId = Long.valueOf(split[0]);
            Integer type = Integer.parseInt(split[1]);
            Long userId = Long.parseLong(split[2]);

            //点赞的状态
            Integer value = (Integer) entry.getValue();
            UpDto upDto = new UpDto(targetId, type, userId, value);
            list.add(upDto);

            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(GlobalConstant.IRH_USER_UP_MAP, key);
        }
        return list;
    }

    @Override
    public List<UpCountDto> getAllUpCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(GlobalConstant.IRH_USER_UP_TOTAL, ScanOptions.NONE);
        List<UpCountDto> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();

            String key = (String)map.getKey();
            UpCountDto dto = new UpCountDto(key, (Long) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(GlobalConstant.IRH_USER_UP_MAP, key);
        }
        return list;
    }

    @Override
    public List<HashSet<Long>> getHotTopicFromRedis(Long topic) {
        ArrayList<HashSet<Long>> res = new ArrayList<>();
        Set<String> set = redisTemplate.opsForZSet().reverseRange(RedisUtil.getHotTopicKey(BrowserType.FORUM), 0, topic - 1);
        HashSet<Long> targetIds = new HashSet<>();
        HashSet<Long> scores = new HashSet<>();
        for (String s : set) {
            long targetId = Long.parseLong(s);
            targetIds.add(targetId);
            Double score = redisTemplate.opsForZSet().score(RedisUtil.getHotTopicKey(BrowserType.FORUM), String.valueOf(targetId));
            scores.add(score.longValue());
        }
        res.add(targetIds);
        res.add(scores);
        return res;

    }

    @Override
    public List<Map.Entry<Long, Long>> getAllCollectCountFromRedis() {
        Cursor<Map.Entry<Long, Long>> cursor = redisTemplate.opsForHash().scan(GlobalConstant.IRH_ARTICLE_COLLECT_MAP, ScanOptions.NONE);
        List<Map.Entry<Long, Long>> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Long, Long> map = cursor.next();
            list.add(map);
            redisTemplate.opsForHash().delete(GlobalConstant.IRH_ARTICLE_COLLECT_MAP, map.getKey());
        }
        return list;
    }
}
