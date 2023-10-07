package com.cai.quartzandactiviti.service;
public interface ProcessService {
    boolean startProcess(String deploymentId,String businessKey);
    String processInstanceId();
}
