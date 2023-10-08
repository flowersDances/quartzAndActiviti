package com.cai.quartzandactiviti.service.impl;

import com.cai.quartzandactiviti.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据部署流程id和业务id启动流程实例
     *
     * @param businessKey 自定义业务流程id（自定义）
     * @return 返回流程启动结果
     */
    @Override
    public boolean startProcess(String businessKey) {
        String deploymentId = (String) redisTemplate.opsForValue().get("deploymentId");
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById(getProcessDefinitionId(deploymentId), businessKey);
        return startProcessInstance(processInstance);
    }

    /**
     * 根据部署流程id和业务id启动流程实例
     *
     * @return 返回流程启动结果
     */
    public boolean startProcess() {
        String deploymentId = (String) redisTemplate.opsForValue().get("deploymentId");
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById(getProcessDefinitionId(deploymentId));
        return startProcessInstance(processInstance);
    }

    /**
     * 根据部署流程id和业务id启动流程实例
     *
     * @param businessKey 自定义业务流程id（自定义）
     * @param map         流程部署时候添加的参数
     * @return 返回流程启动结果
     */
    public boolean startProcess(String businessKey, Map<String, Object> map) {
        String deploymentId = (String) redisTemplate.opsForValue().get("deploymentId");
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceById(getProcessDefinitionId(deploymentId), businessKey, map);
        return startProcessInstance(processInstance);
    }

    /**
     * 获取实例的事件数量
     */
    @Override
    public Integer getEventSize() {
        String processInstanceId = (String) redisTemplate.opsForValue().get("processInstanceId");
        List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
        return activeActivityIds.size();
    }

    /**
     * 根据流程实例获取流程定义id和流程实例id
     *
     * @param processInstance 流程实例
     */
    public void setProcessCacheData(ProcessInstance processInstance) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey("process"))) {
            redisTemplate.delete("process");
        }
        String processDefinitionId = processInstance.getProcessDefinitionId();
        String processInstanceId = processInstance.getProcessInstanceId();
        redisTemplate.opsForHash().put("process", processDefinitionId, processInstanceId);
    }

    /**
     * 根据部署id获取流程定义id
     *
     * @param deploymentId 部署id
     * @return 流程定义id
     */
    private String getProcessDefinitionId(String deploymentId) {
        String processDefinitionId = null;
        List<ProcessDefinition> processDefinitions = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .list();

        for (ProcessDefinition processDefinition : processDefinitions) {
            processDefinitionId = processDefinition.getId();
        }
        return processDefinitionId;
    }

    private boolean startProcessInstance(ProcessInstance processInstance) {
        //把实例id和定义id存到缓存
        redisTemplate.delete("processInstanceId");

        redisTemplate.opsForValue().set("processInstanceId", processInstance.getId());

        setProcessCacheData(processInstance);

        if (!processInstance.getId().isEmpty()){
            log.debug("启动流程实例成功");
            return true;
        }
        log.warn("启动流程实例失败");
        return false;
    }
}
