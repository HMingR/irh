package top.imuster.goods.config;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.imuster.goods.components.DemandBrowserTask;
import top.imuster.goods.components.GoodsBrowserTask;

/**
 * @ClassName: QuartzConfig
 * @Description: 商城模块的定时任务
 * @author: hmr
 * @date: 2020/4/22 9:08
 */
@Component
public class QuartzConfig {

    private static final String GOODS_BROWSER_TASK = "goodsBrowserTask";

    private static final String DEMAND_BROWSER_TASK = "demandBrowserTask";

    @Bean
    public JobDetail goodsBrowserCollect(){
        return JobBuilder.newJob(GoodsBrowserTask.class).withIdentity(GOODS_BROWSER_TASK).storeDurably().build();
    }

    @Bean
    public JobDetail demandBrowserCollect(){
        return JobBuilder.newJob(DemandBrowserTask.class).withIdentity(DEMAND_BROWSER_TASK).storeDurably().build();
    }

    @Bean
    public Trigger goodsBrowserTrigger(@Qualifier("goodsBrowserCollect") JobDetail goodsBrowserCollect){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(180).repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(goodsBrowserCollect)
                .withIdentity(GOODS_BROWSER_TASK)
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public Trigger demandBrowserTrigger(@Qualifier("demandBrowserCollect") JobDetail demandBrowserCollect){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                                                    .simpleSchedule()
                                                    .withIntervalInMinutes(180)
                                                    .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(demandBrowserCollect)
                .withIdentity(DEMAND_BROWSER_TASK)
                .withSchedule(scheduleBuilder)
                .build();
    }



}
