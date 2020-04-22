package top.imuster.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.service.BrowserTimesService;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: GoodsBrowserTimesServiceImpl
 * @Description: GoodsBrowserTimesServiceImpl
 * @author: hmr
 * @date: 2020/4/22 9:29
 */
@Service("goodsBrowserTimesService")
public class GoodsBrowserTimesServiceImpl extends BrowserTimesService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    ProductInfoService productInfoService;

    @Override
    public void generate() {
        List<BrowserTimesDto> browserTimesDtos = getBrowserTimesFromRedis(redisTemplate, BrowserType.ES_SELL_PRODUCT);
        productInfoService.transBrowserTimesFromRedis2DB(browserTimesDtos);
    }
}
