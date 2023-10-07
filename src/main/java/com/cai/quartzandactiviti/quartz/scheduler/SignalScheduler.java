package com.cai.quartzandactiviti.quartz.scheduler;

import com.cai.quartzandactiviti.quartz.job.SignalJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SignalScheduler {
    public void listenerRedisData() {
        //调度器
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            //任务实例
            JobDetail jobDetail = JobBuilder
                    .newJob(SignalJob.class)
                    .withIdentity("signalJob")//定义唯一标识
                    .build();
            //触发器
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("signalTrigger")//定义唯一标识
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
            log.debug("任务调度器启动成功");
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
