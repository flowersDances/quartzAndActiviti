package com.cai.quartzandactiviti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class QuartzAndActivitiApplicationTests {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    /**
     * 部署流程实例
     */
    @Test
    void contextLoads() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/SpringDemo1.bpmn20.xml")
                .addClasspathResource("processes/SpringDemo1.png")
                .name("SpringBoot整合流程图")
                .deploy();
    }

    /**
     * 查询流程实例列表
     */
    @Test
    void listDeployments() {
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery().list();
        if (!deploymentList.isEmpty()) {
            for (Deployment deployment : deploymentList) {
                System.out.println("Id：" + deployment.getId());
                System.out.println("Name：" + deployment.getName());
                System.out.println("DeploymentTime：" + deployment.getDeploymentTime());
                System.out.println("Key：" + deployment.getKey());
            }
        }
    }

    /**
     * 查询流程定义列表
     */
    @Test
    void listProcessDefinitions() {
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();
        if (!processDefinitionList.isEmpty()) {
            for (ProcessDefinition processDefinition : processDefinitionList) {
                System.out.println("Name：" + processDefinition.getName());
                System.out.println("Key：" + processDefinition.getKey());
                System.out.println("ResourceName：" + processDefinition.getResourceName());
                System.out.println("DeploymentId：" + processDefinition.getDeploymentId());
                System.out.println("Version：" + processDefinition.getVersion());
            }
        }
    }

    /**
     * 初始化流程实例
     */
    @Test
    void initProcessInstance() {
        // 流程定义KEY
        String processDefinitionKey = "SpringDemo1:3:a5b3ce71-64b0-11ee-a541-005056c00001";
        // 业务表KEY（用于把业务数据与Activiti7流程数据相关联）
        String businessKey = "4208169753200945";
        // 参数
        Map<String, Object> variables = new HashMap<>(16);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
        System.out.println("流程实例ID：" + processInstance.getProcessInstanceId());
    }

    /**
     * 查询流程实例
     */
    @Test
    void getProcessInstance() {
        String processInstanceId = "SpringDemo1:3:a5b3ce71-64b0-11ee-a541-005056c00001";
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        System.out.println("ProcessInstanceId：" + processInstance.getProcessInstanceId());
        System.out.println("ProcessDefinitionId：" + processInstance.getProcessDefinitionId());
        System.out.println("isEnded：" + processInstance.isEnded());
        System.out.println("isSuspended：" + processInstance.isSuspended());
    }

    /**
     * 查询流程实例列表
     */
    @Test
    void listProcessInstances() {
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().list();
        if (!processInstanceList.isEmpty()) {
            for (ProcessInstance processInstance : processInstanceList) {
                System.out.println("ProcessInstanceId：" + processInstance.getProcessInstanceId());
                System.out.println("ProcessDefinitionId：" + processInstance.getProcessDefinitionId());
                System.out.println("isEnded：" + processInstance.isEnded());
                System.out.println("isSuspended：" + processInstance.isSuspended());
            }
        }
    }

    /**
     * 挂起流程实例
     */
    @Test
    void suspendProcessInstance() {
        runtimeService.suspendProcessInstanceById("a2b64646-5da7-11ee-8d5d-005056c00001");
    }

    /**
     * 激活流程实例
     */
    @Test
    void activeProcessInstance() {
        runtimeService.activateProcessInstanceById("a2b64646-5da7-11ee-8d5d-005056c00001");
    }

    /**
     * 删除流程实例
     */
    @Test
    void deleteProcessInstance() {
        String reason = "测试删除流程实例";
        runtimeService.deleteProcessInstance("a2b64646-5da7-11ee-8d5d-005056c00001", reason);
    }

    /**
     * 查询任务列表
     */
    @Test
    void listTasks() {
        List<Task> taskList = taskService.createTaskQuery().list();
        if (!taskList.isEmpty()) {
            for (Task task : taskList) {
                System.out.println("Id：" + task.getId());
                System.out.println("Name：" + task.getName());
                System.out.println("Assignee：" + task.getAssignee());
            }
        }
    }

    /**
     * 查询指定用户的代办任务
     */
    @Test
    void listTasksByAssignee() {
        String assignee = "admin";
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(assignee).list();
        if (!taskList.isEmpty()) {
            for (Task task : taskList) {
                System.out.println("Id：" + task.getId());
                System.out.println("Name：" + task.getName());
                System.out.println("Assignee：" + task.getAssignee());
            }
        }
    }

    /**
     * 完成任务
     */
    @Test
    void completeTask() {
        String taskId = "a2bb284a-5da7-11ee-8d5d-005056c00001";
        //参数
        Map<String, Object> variables = new HashMap<>(16);
        this.taskService.complete(taskId, variables);
    }

    /**
     * 拾取任务
     */
    @Test
    void claimTask() {
        String taskId = "16beabc1-479f-11ed-9c3a-e4a8dfd43d4a";
        String userId = "jason";
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        taskService.claim(taskId, userId);
    }

    /**
     * 归还任务
     */
    @Test
    void returnTask() {
        String taskId = "16beabc1-479f-11ed-9c3a-e4a8dfd43d4a";
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        // 归还任务
        taskService.unclaim(taskId);
    }


    /**
     * 交办任务
     * 把任务给其他用户
     */
    @Test
    void handoverTask() {
        String taskId = "16beabc1-479f-11ed-9c3a-e4a8dfd43d4a";
        String userId = "jack";
        Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        // 交办任务
        taskService.setAssignee(taskId, userId);
    }

    /**
     * 根据用户名查询历史记录
     */
    @Test
    void listHistoricTasksByAssignee() {
        String assignee = "admin";
        List<HistoricTaskInstance> historicTasks = this.historyService.createHistoricTaskInstanceQuery()
                .orderByHistoricTaskInstanceEndTime()
                .asc()
                .taskAssignee(assignee)
                .list();
        if (!historicTasks.isEmpty()) {
            for (HistoricTaskInstance historicTask : historicTasks) {
                System.out.println("Id：" + historicTask.getId());
                System.out.println("ProcessInstanceId：" + historicTask.getProcessInstanceId());
                System.out.println("Name：" + historicTask.getName());
            }
        }
    }

    /**
     * 根据流程实例ID查询历史
     */
    @Test
    void listHistoricTasksByProcessInstanceId() {
        String processInstanceId = "0f8a9b00-479e-11ed-af85-e4a8dfd43d4a";
        List<HistoricTaskInstance> historicTasks = this.historyService.createHistoricTaskInstanceQuery()
                .orderByHistoricTaskInstanceEndTime()
                .asc()
                .processInstanceId(processInstanceId)
                .list();
        if (!historicTasks.isEmpty()) {
            for (HistoricTaskInstance historicTask : historicTasks) {
                System.out.println("Id：" + historicTask.getId());
                System.out.println("ProcessInstanceId：" + historicTask.getProcessInstanceId());
                System.out.println("Name：" + historicTask.getName());
            }
        }

    }
}
