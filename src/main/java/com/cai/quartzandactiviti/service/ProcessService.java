package com.cai.quartzandactiviti.service;

import java.util.Map;

public interface ProcessService {
    boolean startProcess(String deploymentId,String businessKey);
    boolean startProcess(String deploymentId);
    boolean startProcess(String deploymentId, String businessKey, Map<String,Object> map);
    Integer getEventSize();
}
