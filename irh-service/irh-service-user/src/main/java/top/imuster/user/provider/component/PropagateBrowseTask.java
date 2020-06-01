package top.imuster.user.provider.component;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.user.provider.service.PropagateInfoService;
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
    PropagateInfoService propagateInfoService;

    @Resource
    PropagateBrowseTotalServiceImpl propagateBrowseTotalService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("--------->{}开始执行user模块的propagate浏览统计任务", DateUtil.now());
        propagateBrowseTotalService.generate();
        log.info("--------->{}结束执行user模块的propagate浏览统计任务", DateUtil.now());
    }
}
