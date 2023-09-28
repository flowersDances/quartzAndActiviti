package com.cai.quartzandactiviti;

import org.activiti.api.task.runtime.TaskRuntime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuartzAndActivitiApplicationTests {
    @Autowired
    private TaskRuntime taskRuntime;
    @Test
    void contextLoads() {
        System.out.println("部署对象：");

    }

}
