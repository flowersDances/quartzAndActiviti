package com.cai.quartzandactiviti.service;

public interface DeploymentService {
    /**
     * 读取类路径下文件
     */
    boolean createDeploymentClasspath(String fileName,String filePictureName, String name);

    /**
     * 读取其他位置文件
     */
    boolean createDeployment(String fileName,String filePictureName,String name);

    /**
     * 获取部署流程id
     * @return 部署流程id
     */
    String getDeploymentId();
}
