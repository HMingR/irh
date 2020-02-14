package top.imuster.life.provider.config;


import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.imuster.life.provider.component.HotTopicTask;
import top.imuster.life.provider.component.UpAndCollectTask;


/**
 * @ClassName: QuartzConfig
 * @Description: QuartzConfig
 * @author: hmr
 * @date: 2020/2/8 19:31
 */
@Configuration
public class QuartzConfig {

    @Value("${hot.topic.refreshTime}")
    private int refreshTime;  //热搜总榜的更新周期


    private static final String UP_TASK_QUARTZ = "LikeTaskQuartz";

    private static final String HOT_TOPIC_QUARTZ = "hotTopicQuartz";

    @Bean
    public JobDetail UpQuartzDetail(){
        return JobBuilder.newJob(UpAndCollectTask.class).withIdentity(UP_TASK_QUARTZ).storeDurably().build();
    }

    @Bean
    public JobDetail HotTopicQuartzDetail(){
        return JobBuilder.newJob(HotTopicTask.class).withIdentity(HOT_TOPIC_QUARTZ).storeDurably().build();
    }

    @Bean
    public Trigger UpQuartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInMinutes(20)
                .withIntervalInMinutes(1)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(UpQuartzDetail())
                .withIdentity(UP_TASK_QUARTZ)
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public Trigger HotTopicQuartzTrigger(){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInMinutes(refreshTime)
                .withIntervalInMinutes(1)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(HotTopicQuartzDetail())
                .withIdentity(HOT_TOPIC_QUARTZ)
                .withSchedule(scheduleBuilder)
                .build();
    }
}

