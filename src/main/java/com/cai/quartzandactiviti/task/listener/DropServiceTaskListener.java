package com.cai.quartzandactiviti.task.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

@Slf4j
public class DropServiceTaskListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {
        log.debug("触发销毁任务的监听");

    }
}
