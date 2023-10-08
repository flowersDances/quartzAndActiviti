package com.cai.quartzandactiviti.quartz.job;

import com.cai.quartzandactiviti.service.impl.ProcessServiceImpl;
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

@Component
@Slf4j
@PersistJobDataAfterExecution
public class SignalJob implements Job, DeploymentConstant {
    private static boolean FLAG;
    private static String SIGNAL_NAME;
    private static Integer EVENT_COUNT;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //执行一次检查缓存中是否有key有的话就删除
        FLAG = delSignalKey(FLAG);
        if (hasActiveSignalKey()) {
            //触发信号事件
            signalEventReceived(SIGNAL_NAME);
            //删除key
            getRedisTemplate().delete(SIGNAL);
        }
    }

    /**
     * 触发服务任务
     */
    private void signalEventReceived(String signalName) {
        ProcessTaskServiceImpl processTaskService = (ProcessTaskServiceImpl) SpringContextUtil.getBean("processTaskServiceImpl");
        processTaskService.signalEventReceived(signalName);
    }

    private boolean delSignalKey(boolean flag) {
        if (flag) {
            return true;
        }
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(SIGNAL))) {
            redisTemplate.delete(SIGNAL);
        }
        EVENT_COUNT=getEventSize();
        return true;
    }

    /**
     * 是否激活信号
     */
    private boolean hasActiveSignalKey() {
        Integer getNum;
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate();
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(SIGNAL))) {
            //存入1或者2或者3 都触发服务任务
            getNum = (Integer) operations.get(SIGNAL);
            for (Integer i = 1; i <= EVENT_COUNT; i++) {
                if (i.equals(getNum)) {
                    SIGNAL_NAME = String.valueOf(getNum);
                    log.debug("输出值：" + SIGNAL_NAME);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取redisTemplate
     */
    private RedisTemplate<String, Object> getRedisTemplate() {
        return (RedisTemplate<String, Object>) SpringContextUtil.getBean("redisTemplate");
    }

    private int getEventSize() {
        ProcessServiceImpl processServiceImpl = (ProcessServiceImpl) SpringContextUtil.getBean("processServiceImpl");
        return processServiceImpl.getEventSize();
    }
}
