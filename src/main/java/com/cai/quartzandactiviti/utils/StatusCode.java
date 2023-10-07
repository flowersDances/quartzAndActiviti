package com.cai.quartzandactiviti.utils;

import lombok.Getter;

@Getter
public enum StatusCode {
    SERVICE_ERROR(500, "请求执行失败", "服务器出现故障"),
    CUSTOM_ERROR(600, "服务操作失败", "自定义异常")
        ;
    private final Integer code;
    private final String message;
    private final String comment;

    StatusCode(Integer code, String message, String comment) {
        this.code = code;
        this.message = message;
        this.comment = comment;
    }
}
