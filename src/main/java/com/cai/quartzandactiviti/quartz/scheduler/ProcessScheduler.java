package com.cai.quartzandactiviti.quartz.scheduler;

import com.cai.quartzandactiviti.quartz.job.ProcessJob;
import com.cai.quartzandactiviti.quartz.job.SignalJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProcessScheduler {
    public void listenerRedisData() {
        //调度器
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            //任务实例

            JobDetail jobDetail=JobBuilder
                    .newJob(ProcessJob.class)
                    .withIdentity("processJob")
                    .build();

            //触发器
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("processTrigger")//定义唯一标识
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1))
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);

            scheduler.start();
            log.debug("任务调度器1启动成功");
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
