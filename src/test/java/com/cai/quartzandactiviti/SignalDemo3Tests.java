package com.cai.quartzandactiviti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SignalDemo3Tests {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Test
    void test1() throws InterruptedException {
        repositoryService.createDeployment()
                .addClasspathResource("processes/test1.bpmn20.xml")
                .name("测试")
                .deploy();
    }

    @Test
    void test2() throws InterruptedException {
        //String id="test1:7:b0ceeda8-6736-11ee-a759-005056c00001";
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("test1:1:279ea727-6744-11ee-bacb-005056c00001");
        System.out.println(processInstance.getId());
    }

    @Test
    void test3() throws InterruptedException {
        runtimeService.signalEventReceived("1");
    }
}
