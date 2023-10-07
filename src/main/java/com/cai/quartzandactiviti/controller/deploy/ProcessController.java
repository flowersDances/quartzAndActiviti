package com.cai.quartzandactiviti.controller.deploy;

import com.cai.quartzandactiviti.pojo.entity.ProcessDeploy;
import com.cai.quartzandactiviti.response.Result;
import com.cai.quartzandactiviti.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("process")
public class ProcessController {
    @Autowired
    private ProcessService processService;

    /**
     * 根据部署流程id启动流程实例
     * @param processDeploy 流程实例对象
     */
    @PostMapping("/startProcess")
    public Result startProcessInstance(@RequestBody ProcessDeploy processDeploy){
        String deploymentId=processDeploy.getDeploymentId();
        String businessKey=processDeploy.getBusinessKey();
        boolean startProcessStatus = processService.startProcess(deploymentId, businessKey);
        if (startProcessStatus) {
            String processInstanceId = processService.processInstanceId();
            System.out.println("流程实例id："+processInstanceId);
            return Result.ok("实例启动成功");
        }
        return Result.error("实例启动失败");
    }
}
