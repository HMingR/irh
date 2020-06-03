package top.imuster.user.provider.config;


import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.imuster.user.provider.component.PropagateBrowseTask;

/**
 * @ClassName: PropagateBrowseTask
 * @Description: 广告或通知的浏览次数统计任务
 * @author: hmr
 * @date: 2020/5/31 14:56
 */
@Component
public class QuartzConfig {

    @Value("${propagate.execTime:20}")
    private Integer propagateExecTime;

    private static final String PROPAGATE_BROWSE_TASK = "propagateBrowseTask";

    @Bean
    public JobDetail donationAttribute(){
        return JobBuilder.newJob(PropagateBrowseTask.class).withIdentity(PROPAGATE_BROWSE_TASK).storeDurably().build();
    }

    @Bean
    public Trigger demandBrowserTrigger(@Qualifier("donationAttribute") JobDetail demandBrowserCollect){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInMinutes(20)
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(demandBrowserCollect)
                .withIdentity(PROPAGATE_BROWSE_TASK)
                .withSchedule(scheduleBuilder)
                .build();
    }
}
