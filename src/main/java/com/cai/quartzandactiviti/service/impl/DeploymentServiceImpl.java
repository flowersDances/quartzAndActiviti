package com.cai.quartzandactiviti.service.impl;

import com.cai.quartzandactiviti.service.DeploymentService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Component
public class DeploymentServiceImpl implements DeploymentService {
    @Autowired
    private RepositoryService repositoryService;
    @Value("${file.upload-path}")
    private String filePath;
    /**
     * 部署bpmn文件
     * @param fileName 流程文件名
     * @param name 部署流程名
     * @return 部署结果
     */
    @Override
    public boolean createDeploymentClasspath(String fileName, String name) {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(filePath+fileName)
                .name(name)
                .deploy();
        if (deployment.getId().isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public boolean createDeployment(String fileName,String name) {
        try {
            File file = new File(filePath+fileName);
            FileInputStream fis=new FileInputStream(file);
            DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().addInputStream(name, fis);
            Deployment deploy = deploymentBuilder.deploy();
            System.out.println(deploy.getId());
            if (deploy.getId().isEmpty()){
                return false;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
