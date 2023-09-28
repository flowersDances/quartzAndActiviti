package com.cai.quartzandactiviti.utils;

import com.cai.quartzandactiviti.response.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(CustomException.class)
    public Result handleCustomException(CustomException ex) {
        // 处理自定义异常
        return Result.error(StatusCode.CUSTOM_ERROR.getCode(),StatusCode.CUSTOM_ERROR.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    public Result handleThrowable(){
        return Result.error(StatusCode.SERVICE_ERROR.getCode(), StatusCode.SERVICE_ERROR.getMessage());
    }
}
