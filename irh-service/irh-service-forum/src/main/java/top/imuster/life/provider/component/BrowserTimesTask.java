package top.imuster.life.provider.component;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import top.imuster.common.core.service.BrowserTimesService;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.life.provider.service.ArticleForwardInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: BrowserTimesTask
 * @Description: BrowserTimesTask
 * @author: hmr
 * @date: 2020/2/15 16:56
 */
@Component
public class BrowserTimesTask extends QuartzJobBean {

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BrowserTimesService browserTimesService;

    @Resource
    ArticleForwardInfoService articleForwardInfoService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("---------->{}开始批量处理浏览记录和转发记录", DateUtil.now());
        browserTimesService.generate();
        articleForwardInfoService.transForwardTimesFromRedis2DB();
        log.info("---------->{}批量处理浏览记录和转发记录完成", DateUtil.now());
    }
}
