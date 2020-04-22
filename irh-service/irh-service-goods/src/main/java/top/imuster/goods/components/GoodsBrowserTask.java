package top.imuster.goods.components;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import top.imuster.common.core.utils.DateUtils;
import top.imuster.goods.service.impl.GoodsBrowserTimesServiceImpl;

import javax.annotation.Resource;

/**
 * @ClassName: GoodsBrowserTask
 * @Description: 商城浏览记录任务
 * @author: hmr
 * @date: 2020/4/22 9:12
 */
@Component
public class GoodsBrowserTask extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(GoodsBrowserTask.class);

    @Resource
    GoodsBrowserTimesServiceImpl goodsBrowserTimesService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("{}---->开始执行商城浏览记录的定时任务", DateUtils.now());
        goodsBrowserTimesService.generate();
        log.info("{}---->完成执行商城浏览记录的定时任务", DateUtils.now());

    }
}
