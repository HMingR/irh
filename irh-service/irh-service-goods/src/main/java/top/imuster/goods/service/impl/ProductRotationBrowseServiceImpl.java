package top.imuster.goods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.service.BrowserTimesService;
import top.imuster.goods.service.ProductRotationImgInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: ProductRotationBrowseServiceImpl
 * @Description: TODO
 * @author: hmr
 * @date: 2020/6/1 16:02
 */
@Service("productRotationBrowseService")
public class ProductRotationBrowseServiceImpl extends BrowserTimesService {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    ProductRotationImgInfoService productRotationImgInfoService;

    @Override
    public void generate() {
        List<BrowserTimesDto> res = getBrowserTimesFromRedis(redisTemplate, BrowserType.PRODUCT_ROTATION);
        Integer total = productRotationImgInfoService.saveClick2DB(res);
        log.info("--------->共更新了{}条点击记录", total);
    }

}
