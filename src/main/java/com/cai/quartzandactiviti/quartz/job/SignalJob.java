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
        log.debug("SignalJob执行中...");
        RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) SpringContextUtil.getBean("redisTemplate");
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(SIGNAL))) {
            if (Objects.equals(operations.get(SIGNAL), SIGNAL_ACTIVE_VALUE)) {
                //触发信号事件
                log.debug("信号事件准备触发");
                signalEventReceived();
                log.debug("信号事件完成触发");
                //删除key
                redisTemplate.delete(SIGNAL);
            }
        }
    }

    public void signalEventReceived() {
        ProcessTaskServiceImpl processTaskService = (ProcessTaskServiceImpl) SpringContextUtil.getBean("processTaskServiceImpl");
        processTaskService.signalEventReceived(SIGNAL_NAME);
    }

    public boolean delSignalKey(boolean flag){
        if (flag){
            return true;
        }
        RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) SpringContextUtil.getBean("redisTemplate");
        if (Boolean.TRUE.equals(redisTemplate.hasKey(SIGNAL))) {
            redisTemplate.delete(SIGNAL);
        }
        return true;
    }
}
