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
import java.io.InputStream;

@Component
public class DeploymentServiceImpl implements DeploymentService {
    @Autowired
    private RepositoryService repositoryService;
    @Value("${file.upload-path}")
    private String filePath;
    @Value("${bpmn.fileClassPath}")
    private String fileClassPath;
    /**
     * 部署bpmn文件
     * @param fileName 流程文件名
     * @param name 部署流程名
     * @return 部署结果
     */
    @Override
    public boolean createDeploymentClasspath(String fileName,String filePictureName, String name) {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(filePath+fileName)
                .addClasspathResource(fileClassPath+"/"+filePictureName)
                .name(name)
                .deploy();
        if (deployment.getId().isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public boolean createDeployment(String fileName,String filePictureName,String name) {
        try {
            InputStream inputStream1 = new FileInputStream(filePath + fileName);
            InputStream inputStream2 = new FileInputStream(filePath + filePictureName);
            Deployment deploy = repositoryService.createDeployment()
                    //参数1 用于识别资源 参数2 bpmn文件
                    .addInputStream(fileName, inputStream1)
                    .addInputStream(filePictureName, inputStream2).name(name).deploy();
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
