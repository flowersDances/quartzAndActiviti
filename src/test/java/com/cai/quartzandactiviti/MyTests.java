package com.cai.quartzandactiviti;

import com.cai.quartzandactiviti.service.DeploymentService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class MyTests {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private DeploymentService deploymentService;

    @Test
    void contextLoads() {
        deploymentService.createDeploymentClasspath(
                "SpringDemo1.bpmn20.xml",
                "SpringDemo1.png",
                "springboot整合流程");
        System.out.println(deploymentService.getDeploymentId());
    }

    @Test
    public void startProcess(){
        String processDefinitionKey = "SpringDemo1:4:1aad2686-64b4-11ee-a493-005056c00001";
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionKey);
        System.out.println("流程实例ID：" + processInstance.getProcessInstanceId());
    }
}
