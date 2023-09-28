package com.cai.quartzandactiviti.service.impl;

import com.cai.quartzandactiviti.service.FlowService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FlowServiceImpl implements FlowService {
    @Value("${bpmn.filePath}")
    private String filePath;
    /**
     * 部署bpmn文件
     * @param fileName 文件名
     * @return 部署结果
     */
    @Override
    public String createDeployment(String fileName,String name) {
        ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
        Deployment deploy = processEngine
                .getRepositoryService()
                .createDeployment()
                //.addClasspathResource(filePath+"/"+fileName)
                .addClasspathResource("activiti.cfg.xml")
                .name("121")
                .deploy();
        if (deploy.getId().isEmpty()) {
            return "部署失败";
        }
        return "部署成功";
    }
}
