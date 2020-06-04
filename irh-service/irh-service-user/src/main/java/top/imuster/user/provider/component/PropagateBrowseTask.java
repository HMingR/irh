package top.imuster.user.provider.component;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import top.imuster.user.provider.service.impl.PropagateBrowseTotalServiceImpl;

import javax.annotation.Resource;

/**
 * @ClassName: PropagateBrowseTask
 * @Description: PropagateBrowseTask
 * @author: hmr
 * @date: 2020/5/31 15:02
 */
@Component
public class PropagateBrowseTask extends QuartzJobBean {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    PropagateBrowseTotalServiceImpl propagateBrowseTotalService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        propagateBrowseTotalService.generate();
    }
}
