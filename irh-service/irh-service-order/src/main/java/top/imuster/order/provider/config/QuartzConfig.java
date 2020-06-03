package top.imuster.order.provider.config;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.imuster.order.provider.components.DonationApplyAttributeTask;

/**
 * @ClassName: QuartzConfig
 * @Description: order模块的定时任务
 * @author: hmr
 * @date: 2020/4/22 9:08
 */
@Component
public class QuartzConfig {

    @Value("${donation.execTime:30}")
    private Integer donationExecTime;

    private static final String DONATION_ATTRIBUTE_TASK = "donationAttributeTask";

    @Bean
    public JobDetail donationAttribute(){
        return JobBuilder.newJob(DonationApplyAttributeTask.class).withIdentity(DONATION_ATTRIBUTE_TASK).storeDurably().build();
    }

    @Bean
    @Autowired
    public Trigger goodsBrowserTrigger(JobDetail donationAttribute){
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(40).repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(donationAttribute)
                .withIdentity(DONATION_ATTRIBUTE_TASK)
                .withSchedule(scheduleBuilder)
                .build();
    }
}
