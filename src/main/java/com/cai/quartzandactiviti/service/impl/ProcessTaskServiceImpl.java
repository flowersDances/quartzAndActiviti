package com.cai.quartzandactiviti.service.impl;

import com.cai.quartzandactiviti.service.ProcessTaskService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ProcessTaskServiceImpl implements ProcessTaskService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private HistoryService historyService;

    @Override
    public void signalEventReceived(String signalName) {
        runtimeService.signalEventReceived(signalName);
    }

    @Override
    public boolean isCompleteTask() {
        //从缓存中获取流程定义id
        String historicProcessDefinitionId;
        String cacheDefinitionId = null;
        Set<Map.Entry<Object, Object>> process = redisTemplate.opsForHash().entries("process").entrySet();
        for (Map.Entry<Object, Object> objectObjectEntry : process) {
            cacheDefinitionId = (String) objectObjectEntry.getKey();
        }
        //从历史记录获取流程定义id 如果当前流程定义id没有执行完毕返回false
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().list();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            if (historicProcessInstance.getEndTime() == null) {
                historicProcessDefinitionId = historicProcessInstance.getProcessDefinitionId();
                if (historicProcessDefinitionId.equals(cacheDefinitionId)) {
                    return false;
                }
            }
        }
        return true;
    }
}
