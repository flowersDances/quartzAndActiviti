package com.cai.quartzandactiviti.controller.deploy;

import com.cai.quartzandactiviti.pojo.entity.ProcessFile;
import com.cai.quartzandactiviti.response.Result;
import com.cai.quartzandactiviti.service.DeploymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("bpmn")
public class DeployController {
    @Autowired
    DeploymentService deploymentService;

    /**
     * 根据流程图部署文件
     */
    @PostMapping("deploy")
    public Result deployFile(@RequestBody ProcessFile processFile) {
        // bpmn文件名称
        String fileName = processFile.getProcessFileName();
        // 图片名称
        String filePictureName = processFile.getFilePictureName();
        //流程部署
        boolean deployment = deploymentService.createDeployment(fileName, filePictureName, fileName);
        if (deployment) {
            return Result.ok();
        }
        return Result.error();
    }
}
