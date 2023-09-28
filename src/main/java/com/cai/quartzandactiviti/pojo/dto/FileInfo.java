package com.cai.quartzandactiviti.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class FileInfo {
    @NotEmpty
    @NotBlank(message = "文件名输入有误")
    private String fileName;
    private String name;
}
