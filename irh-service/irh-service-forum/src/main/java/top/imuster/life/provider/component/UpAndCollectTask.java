package top.imuster.life.provider.component;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.life.provider.service.ArticleCollectionService;
import top.imuster.life.provider.service.UserForumAttributeService;

import javax.annotation.Resource;

/**
 * @ClassName: UpAndCollectTask
 * @Description: UpAndCollectTask 实现定时任务的逻辑处理类
 * @author: hmr
 * @date: 2020/2/8 19:32
 */
@Component
public class UpAndCollectTask extends QuartzJobBean {

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    UserForumAttributeService userForumAttributeService;

    @Resource
    ArticleCollectionService articleCollectionService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始执行处理点赞和收藏的定时任务,开始执行定时任务的时间为{}", DateUtil.now());
        //将点赞记录保存到数据库
        userForumAttributeService.transUpFromRedis2DB();
        //将点赞总数更新到各个具体的表中
        userForumAttributeService.transUpCountFromRedis2DB();
        //将收藏记录更新到文章表
        articleCollectionService.transCollectCountFromRedis2Db();
        log.info("点赞和收藏的定时任务处理完成,完成的时间为{}", DateUtil.now());
    }
}
