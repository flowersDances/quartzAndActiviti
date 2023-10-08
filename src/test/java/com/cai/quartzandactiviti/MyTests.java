package com.cai.quartzandactiviti;

import com.cai.quartzandactiviti.service.DeploymentService;
import com.cai.quartzandactiviti.service.ProcessService;
import com.cai.quartzandactiviti.service.ProcessTaskService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class MyTests {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private DeploymentService deploymentService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessService processService;

    @Autowired
    private ProcessTaskService processTaskService;

    //部署
    @Test
    void contextLoads() {
        deploymentService.createDeploymentClasspath(
                "SignalDemo2.bpmn20.xml",
                "SignalDemo2.png",
                "springboot整合流程-并行网关");
        System.out.println(deploymentService.getDeploymentId());
    }

    //启动
    @Test
    public void listSignals() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("SignalDemo2:1:8dc6e5c1-6577-11ee-a7d3-005056c00001");
        processInstance.getProcessInstanceId();
        if (processInstance.getId().isEmpty()){
            log.warn("启动流程实例失败");
        }
        log.debug("启动流程实例成功");
    }

    /**
     * 查看任务
     */
    @Test
    public void showVersion() {
        List<ProcessInstance> runningInstances = runtimeService.createProcessInstanceQuery().list();
        for (ProcessInstance instance : runningInstances) {
            String executionId = instance.getId();
            // 处理每个流程实例的执行对象
            log.debug(executionId);
        }
    }
    @Test
    public void getActiviti(){
        List<String> activeActivityIds = runtimeService.getActiveActivityIds("e3624e11-657a-11ee-adc0-005056c00001");
        System.out.println(activeActivityIds);
        //得到对应的三个信号的activitiId
    }

    @Test
    public void SignalActive(){
        //processTaskService.signalEventReceived("1");
        //processTaskService.signalEventReceived("2");
        processTaskService.signalEventReceived("3");
    }
}
