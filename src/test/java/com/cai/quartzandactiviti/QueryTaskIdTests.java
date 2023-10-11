package com.cai.quartzandactiviti;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class QueryTaskIdTests {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Test
    void test1(){
        // 根据流程定义key和任务名称查询服务任务2
        String processDefinitionKey = "SignalDemo3"; // 替换成你的流程定义key
        String taskName = "服务任务2"; // 替换成你的服务任务名称
        long count = taskService.createTaskQuery().processDefinitionId("SignalDemo3:2:2ef68c1e-6753-11ee-a3fa-005056c00001").count();
        System.out.println(count);
    }
}
