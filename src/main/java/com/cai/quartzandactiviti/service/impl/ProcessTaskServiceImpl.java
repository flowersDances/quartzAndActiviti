package com.cai.quartzandactiviti.service.impl;

import com.cai.quartzandactiviti.service.ProcessTaskService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessTaskServiceImpl implements ProcessTaskService {
    @Autowired
    private RuntimeService runtimeService;
    @Override
    public void signalEventReceived(String signalName) {
        runtimeService.signalEventReceived(signalName);
    }
}
