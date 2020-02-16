package top.imuster.life.provider.component;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import top.imuster.common.core.service.BrowserTimesService;
import top.imuster.common.core.utils.DateUtils;

/**
 * @ClassName: BrowserTimesTask
 * @Description: BrowserTimesTask
 * @author: hmr
 * @date: 2020/2/15 16:56
 */
@Component
@Slf4j
public class BrowserTimesTask extends QuartzJobBean {

    @Autowired
    BrowserTimesService browserTimesService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("{}开始批量处理浏览记录", DateUtils.now());
        browserTimesService.generate();
        log.info("{}批量处理浏览记录处理完成", DateUtils.now());
    }
}
