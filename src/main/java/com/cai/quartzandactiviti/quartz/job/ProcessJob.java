package com.cai.quartzandactiviti.quartz.job;

import com.cai.quartzandactiviti.service.impl.ProcessTaskServiceImpl;
import com.cai.quartzandactiviti.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class ProcessJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ProcessTaskServiceImpl processTaskServiceImpl = (ProcessTaskServiceImpl) SpringContextUtil.getBean("processTaskServiceImpl");
        if (!processTaskServiceImpl.isCompleteTask()) {
            log.debug("流程正在执行...");
            return;
        }
        log.debug("流程执行完毕...");
    }

}
