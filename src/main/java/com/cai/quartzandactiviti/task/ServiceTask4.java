package com.cai.quartzandactiviti.task;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

@Slf4j
public class ServiceTask4 implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        log.debug("触发服务任务4【ServiceTask4】");
    }
}
