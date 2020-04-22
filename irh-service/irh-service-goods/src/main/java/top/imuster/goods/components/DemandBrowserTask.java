package top.imuster.goods.components;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import top.imuster.common.core.service.BrowserTimesService;
import top.imuster.common.core.utils.DateUtils;

import javax.annotation.Resource;

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

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("{}---->开始执行商城浏览记录的定时任务", DateUtils.now());
        demandBrowserTimesService.generate();
        log.info("{}---->完成执行商城浏览记录的定时任务", DateUtils.now());

    }
}
