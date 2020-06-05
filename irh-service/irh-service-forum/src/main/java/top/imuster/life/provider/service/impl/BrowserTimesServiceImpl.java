package top.imuster.life.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.service.BrowserTimesService;
import top.imuster.life.provider.service.ArticleInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: BrowserTimesServiceImpl
 * @Description: BrowserTimesServiceImpl
 * @author: hmr
 * @date: 2020/2/15 17:01
 */
@Service("browserTimesService")
public class BrowserTimesServiceImpl extends BrowserTimesService {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    ArticleInfoService articleInfoService;

    @Override
    public void generate() {
        List<BrowserTimesDto> res = getBrowserTimesFromRedis(BrowserType.FORUM);
        articleInfoService.updateBrowserTimesFromRedis2Redis(res);
    }

}
