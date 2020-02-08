package top.imuster.forum.provider.component;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import top.imuster.common.core.utils.DateUtils;
import top.imuster.forum.provider.service.UserForumAttributeService;

import javax.annotation.Resource;

/**
 * @ClassName: UpTask
 * @Description: UpTask 实现定时任务的逻辑处理类
 * @author: hmr
 * @date: 2020/2/8 19:32
 */
@Slf4j
public class UpTask extends QuartzJobBean {

    @Resource
    UserForumAttributeService userForumAttributeService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始执行定时任务,定时任务的执行时间为{}", DateUtils.now());
        //将点赞记录保存到数据库
        userForumAttributeService.transUpFromRedis2DB();
        //将点赞总数更新到各个具体的表中
        userForumAttributeService.transUpCountFromRedis2DB();
    }
}
