package top.imuster.forum.provider.component;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import top.imuster.common.core.utils.DateUtils;
import top.imuster.forum.provider.service.ArticleCollectionService;
import top.imuster.forum.provider.service.UserForumAttributeService;

import javax.annotation.Resource;

/**
 * @ClassName: UpAndCollectTask
 * @Description: UpAndCollectTask 实现定时任务的逻辑处理类
 * @author: hmr
 * @date: 2020/2/8 19:32
 */
@Slf4j
@Component
public class UpAndCollectTask extends QuartzJobBean {

    @Resource
    UserForumAttributeService userForumAttributeService;

    @Resource
    ArticleCollectionService articleCollectionService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始执行定时任务,定时任务的执行时间为{}", DateUtils.now());
        //将点赞记录保存到数据库
        userForumAttributeService.transUpFromRedis2DB();
        //将点赞总数更新到各个具体的表中
        userForumAttributeService.transUpCountFromRedis2DB();

        //将收藏记录更新到文章表
        articleCollectionService.transCollectCountFromRedis2Db();
    }
}
