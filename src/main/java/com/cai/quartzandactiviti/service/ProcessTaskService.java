package com.cai.quartzandactiviti.service;

public interface ProcessTaskService {
    /**
     * 触发信号事件
     * @param signalName 信号名
     */
    void signalEventReceived(String signalName);
}
