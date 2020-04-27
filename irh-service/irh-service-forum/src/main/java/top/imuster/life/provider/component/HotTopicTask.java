package top.imuster.life.provider.component;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.life.provider.service.UserForumAttributeService;

import javax.annotation.Resource;

/**
 * @ClassName: HotTopicTask
 * @Description: 热搜定时任务
 * @author: hmr
 * @date: 2020/2/14 10:08
 */
@Component
public class HotTopicTask extends QuartzJobBean {

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    //显示热搜的数量
    private static Long HOT_TOPIC_NUM = 30L;

    @Value("${hot.topic.total}")
    private Long total;

    @Resource
    UserForumAttributeService userForumAttributeService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("{}开始执行热搜统计定时任务", DateUtil.now());
        userForumAttributeService.transHotTopicFromRedis2DB(HOT_TOPIC_NUM);
        log.info("{}结束热搜统计任务", DateUtil.now());
    }
}
