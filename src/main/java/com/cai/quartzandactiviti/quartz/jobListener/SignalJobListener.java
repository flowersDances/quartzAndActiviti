package com.cai.quartzandactiviti.quartz.jobListener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SignalJobListener implements JobListener {
    @Override
    public String getName() {
        return "SignalJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        //try {
        //    //context.getScheduler().deleteJob(context.getJobDetail().getKey());
        //} catch (SchedulerException e) {
        //    throw new RuntimeException(e);
        //}
    }
}
