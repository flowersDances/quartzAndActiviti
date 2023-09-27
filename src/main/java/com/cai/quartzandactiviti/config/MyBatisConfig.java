package com.cai.quartzandactiviti.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@MapperScan("com.cai.quartzandactiviti")
public class MyBatisConfig {
    public MyBatisConfig() {
        log.debug("创建了【{}}】",this.getClass().getSimpleName());
    }
}
