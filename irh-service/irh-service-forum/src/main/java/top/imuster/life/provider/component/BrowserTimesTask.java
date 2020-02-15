package top.imuster.life.provider.component;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @ClassName: BrowserTimesTask
 * @Description: BrowserTimesTask
 * @author: hmr
 * @date: 2020/2/15 16:56
 */
@Component
@Slf4j
public class BrowserTimesTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
