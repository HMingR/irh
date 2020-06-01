package top.imuster.goods.components;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import top.imuster.common.core.service.BrowserTimesService;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.goods.api.dto.GoodsForwardDto;
import top.imuster.goods.service.ProductDemandInfoService;
import top.imuster.goods.service.RedisOperateService;
import top.imuster.goods.service.impl.ProductRotationBrowseServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: DemandBrowserTask
 * @Description: 需求浏览次数
 * @author: hmr
 * @date: 2020/4/22 9:21
 */
@Component
public class DemandBrowserTask extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(DemandBrowserTask.class);

    @Resource
    BrowserTimesService demandBrowserTimesService;

    @Resource
    RedisOperateService redisOperateService;

    @Resource
    ProductDemandInfoService productDemandInfoService;

    @Resource
    ProductRotationBrowseServiceImpl productRotationBrowseService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("{}---->开始执行商城浏览记录的定时任务", DateUtil.now());
        demandBrowserTimesService.generate();
        log.info("{}---->完成执行商城浏览记录的定时任务", DateUtil.now());

        log.info("{}---->开始执行商城需求收藏的定时任务", DateUtil.now());
        List<GoodsForwardDto> demandCollectDto = redisOperateService.transCollect2DB(2);
        productDemandInfoService.updateDemandCollectTotal(demandCollectDto);
        log.info("{}---->完成执行商城需求收藏的定时任务", DateUtil.now());

        log.info("{}---->开始执行商城首页轮播图点击次数统计的定时任务", DateUtil.now());
        productRotationBrowseService.generate();
        log.info("{}---->完成执行商城首页轮播图点击次数统计的定时任务", DateUtil.now());

    }
}
