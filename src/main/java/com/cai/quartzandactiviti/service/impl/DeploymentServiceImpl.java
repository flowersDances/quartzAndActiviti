package com.cai.quartzandactiviti.service.impl;

import com.cai.quartzandactiviti.service.DeploymentService;
import com.cai.quartzandactiviti.utils.DeploymentConstant;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@Slf4j
public class DeploymentServiceImpl implements DeploymentService, DeploymentConstant {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private static Integer VERSION = 1;
    @Autowired
    private RepositoryService repositoryService;
    @Value("${file.upload-path}")
    private String filePath;
    @Value("${bpmn.fileClassPath}")
    private String fileClassPath;
    /**
     * 部署bpmn文件 类路径下部署
     * @param fileName 流程文件名
     * @param name     部署流程名
     * @return 部署结果
     */
    @Override
    public boolean createDeploymentClasspath(String fileName, String filePictureName, String name) {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(fileClassPath + "/" + fileName)
                .addClasspathResource(fileClassPath + "/" + filePictureName)
                .name(name)
                .deploy();
        if (deployment.getId().isEmpty()) {
            log.warn("{} 流程文件部署失败", fileName);
            return false;
        }
        //获取流程部署id 存入到缓存
        redisTemplate.opsForValue().set("deploymentId",deployment.getId());
        log.debug("{} 流程文件部署成功", fileName);
        return true;
    }

    /**
     * 部署bpmn文件 其他路径下部署
     * @param fileName 流程文件名
     * @param name     部署流程名
     * @return 部署结果
     */
    @Override
    public boolean createDeployment(String fileName, String filePictureName, String name) {
        try {
            InputStream inputStream1 = Files.newInputStream(Paths.get(filePath + fileName));
            InputStream inputStream2 = Files.newInputStream(Paths.get(filePath + filePictureName));
            Deployment deploy = repositoryService.createDeployment()
                    //参数1 用于识别资源 参数2 bpmn文件
                    .addInputStream(fileName, inputStream1)
                    .addInputStream(filePictureName, inputStream2).name(name).deploy();
            //获取流程部署id 存入到缓存
            redisTemplate.opsForValue().set("deploymentId",deploy.getId());
            log.debug("{} 流程文件部署成功", fileName);
            inputStream1.close();
            inputStream2.close();
            if (deploy.getId().isEmpty()) {
                log.warn("{} 流程文件部署失败", fileName);
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
