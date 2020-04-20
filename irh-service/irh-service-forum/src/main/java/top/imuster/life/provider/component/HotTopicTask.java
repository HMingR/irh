package top.imuster.life.provider.component;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import top.imuster.common.core.utils.DateUtils;
import top.imuster.life.provider.service.UserForumAttributeService;

import javax.annotation.Resource;

/**
 * @ClassName: HotTopicTask
 * @Description: 热搜定时任务
 * @author: hmr
 * @date: 2020/2/14 10:08
 */
@Component
@Slf4j
public class HotTopicTask extends QuartzJobBean {

    //显示热搜的数量
    private static Long HOT_TOPIC_NUM = 30L;

    @Value("${hot.topic.total}")
    private Long total;

    @Resource
    UserForumAttributeService userForumAttributeService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("{}开始执行热搜统计定时任务", DateUtils.now());
        userForumAttributeService.transHotTopicFromRedis2DB(HOT_TOPIC_NUM);
        log.info("{}结束热搜统计任务", DateUtils.now());
    }
}
