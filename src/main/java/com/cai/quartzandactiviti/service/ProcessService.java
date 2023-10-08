package com.cai.quartzandactiviti.service;

import java.util.Map;

public interface ProcessService {
    boolean startProcess(String businessKey);
    boolean startProcess();
    boolean startProcess(String businessKey, Map<String,Object> map);
    Integer getEventSize();
}
