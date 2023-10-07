package com.cai.quartzandactiviti.service.impl;

import com.cai.quartzandactiviti.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    private RuntimeService runtimeService;
    private static String PROCESS_INSTANCE_ID;
    /**
     * 根据部署流程id和业务id启动流程实例
     * @param deploymentId 流程id
     * @param businessKey 自定义业务流程id（自定义）
     * @return 返回流程启动结果
     */
    @Override
    public boolean startProcess(String deploymentId,String businessKey) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(deploymentId, businessKey);
        PROCESS_INSTANCE_ID=processInstance.getProcessInstanceId();
        if (PROCESS_INSTANCE_ID.isEmpty()){
            log.warn("启动流程实例失败");
            return false;
        }
        log.debug("启动流程实例成功");
        return true;
    }

    /**
     * 获取流程实例id
     */
    @Override
    public String processInstanceId() {
        return PROCESS_INSTANCE_ID;
    }
}
