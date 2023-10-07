package com.cai.quartzandactiviti;

import com.cai.quartzandactiviti.service.DeploymentService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
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
        String deploymentId = "72a86d7f-64b6-11ee-bdad-005056c00001";
        String prefix="";
        String version="";
        String suffix=deploymentId;
        String processInstanceById=prefix+":"+version+":"+suffix;
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processInstanceById);
        System.out.println("流程实例ID：" + processInstance.getProcessInstanceId());
    }

    @Test
    public void showVersion(){
        List<ProcessDefinition> processDefinitions = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId("b2728fa9-64be-11ee-8a21-005056c00001")
                .list();

        for (ProcessDefinition processDefinition : processDefinitions) {
            System.out.println("流程定义ID: " + processDefinition.getId());
            System.out.println("流程定义键: " + processDefinition.getKey());
            System.out.println("流程定义版本: " + processDefinition.getVersion());
        }
    }
}
