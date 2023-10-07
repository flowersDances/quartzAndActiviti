package com.cai.quartzandactiviti.quartz.job;

import com.cai.quartzandactiviti.service.ProcessTaskService;
import com.cai.quartzandactiviti.service.impl.ProcessTaskServiceImpl;
import com.cai.quartzandactiviti.utils.DeploymentConstant;
import com.cai.quartzandactiviti.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
@PersistJobDataAfterExecution
public class SignalJob implements Job, DeploymentConstant {
    private static boolean FLAG;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //执行一次检查缓存中是否有key有的话就删除
        FLAG=delSignalKey(FLAG);
        if (hasActiveSignalKey()) {
            //触发信号事件
            signalEventReceived();
            //删除key
            getRedisTemplate().delete(SIGNAL);
        }
    }
    /**
     * 触发服务任务
     */
    private void signalEventReceived() {
        ProcessTaskServiceImpl processTaskService = (ProcessTaskServiceImpl) SpringContextUtil.getBean("processTaskServiceImpl");
        processTaskService.signalEventReceived(SIGNAL_NAME);
    }
    private boolean delSignalKey(boolean flag){
        if (flag){
            return true;
        }
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(SIGNAL))) {
            redisTemplate.delete(SIGNAL);
        }
        return true;
    }
    /**
     * 是否激活信号
     */
    private boolean hasActiveSignalKey(){
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(SIGNAL))) {
            if (Objects.equals(operations.get(SIGNAL), SIGNAL_ACTIVE_VALUE)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取redisTemplate
     */
    private RedisTemplate<String, Object> getRedisTemplate(){
        return (RedisTemplate<String, Object>) SpringContextUtil.getBean("redisTemplate");
    }
}
