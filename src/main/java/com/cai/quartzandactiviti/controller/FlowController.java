package com.cai.quartzandactiviti.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class FlowController {
    @ApiOperation("部署")
    @PostMapping("/flow/")
    public String createDeployment(){

        return "部署成功";
    }
}
