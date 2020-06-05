package top.imuster.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.service.BrowserTimesService;
import top.imuster.goods.service.ProductDemandInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: DemandBrowserTimesServiceImpl
 * @Description: DemandBrowserTimesServiceImpl
 * @author: hmr
 * @date: 2020/4/22 9:32
 */
@Service("demandBrowserTimesService")
public class DemandBrowserTimesServiceImpl extends BrowserTimesService {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    ProductDemandInfoService productDemandInfoService;

    @Override
    public void generate() {
        List<BrowserTimesDto> browserTimesDtos = getBrowserTimesFromRedis(BrowserType.ES_DEMAND_PRODUCT);
        productDemandInfoService.transBrowserTimesFromRedis2DB(browserTimesDtos);

    }
}
