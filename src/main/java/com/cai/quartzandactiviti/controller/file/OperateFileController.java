package com.cai.quartzandactiviti.controller.file;

import com.cai.quartzandactiviti.response.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
@Api(tags = "操作文件")
@RestController
@RequestMapping("/operate")
@Slf4j
public class OperateFileController {
    @Value("${file.upload-path}")
    private String uploadPath;

    /**
     * 上传文件
     * @param file 文件
     * @return 返回上传结果
     */
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) throws IOException{
        // 处理上传的文件
        if (!file.isEmpty()) {
            String uploadFileName=file.getOriginalFilename();
            log.debug("上传路径：{},上传文件：{}",uploadPath,uploadFileName);
            File destFile = new File(uploadPath + uploadFileName);
            // 将上传的文件写入到目标文件
            file.transferTo(destFile);
            // 文件上传成功
            return Result.ok("fileName",uploadFileName);
        }
        return Result.error();
    }
}