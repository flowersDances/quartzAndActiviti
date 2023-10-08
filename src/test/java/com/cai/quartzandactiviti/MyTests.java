package com.cai.quartzandactiviti;

import com.cai.quartzandactiviti.service.DeploymentService;
import com.cai.quartzandactiviti.service.ProcessService;
import com.cai.quartzandactiviti.service.ProcessTaskService;
import com.cai.quartzandactiviti.service.impl.ProcessTaskServiceImpl;
import com.cai.quartzandactiviti.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private HistoryService historyService;

    //部署
    @Test
    void contextLoads() {
        deploymentService.createDeploymentClasspath(
                "SignalDemo2.bpmn20.xml",
                "SignalDemo2.png",
                "springboot整合流程-并行网关");
    }

    //启动
    @Test
    public void listSignals() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceById("38f1ba7c-65a1-11ee-a71a-005056c00001");
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


    /**
     * 部署流程后根据得到的deploymentId查询processDefinitionId
     * 例如查询根据 fea46d08-65a2-11ee-a5a9-005056c00001 -->
     * SignalDemo2:1:febfe44b-65a2-11ee-a5a9-005056c00001
     */
    @Test
    void getProcessDefinitionId() {
        String processDefinitionId = null;
        String deploymentId = "fea46d08-65a2-11ee-a5a9-005056c00001";
        List<ProcessDefinition> processDefinitions = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .list();

        for (ProcessDefinition processDefinition : processDefinitions) {
            processDefinitionId = processDefinition.getId();
        }
        System.out.println(processDefinitionId);
    }


    //查看流程没有结束的流程定义id
    @Test
    void isComplete() {
        //List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().list();
        //for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
        //    if (historicProcessInstance.getEndTime()==null){
        //        System.out.println(historicProcessInstance.getProcessDefinitionId());
        //    }
        //}

    }
}
