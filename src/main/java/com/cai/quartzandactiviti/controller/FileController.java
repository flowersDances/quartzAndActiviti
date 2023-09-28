package com.cai.quartzandactiviti.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Value("${user.home}")
    private String userHome;// C:\Users\RuoLi
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException{
        // 处理上传的文件
        if (!file.isEmpty()) {
            String uploadFileName=file.getOriginalFilename();
            log.debug("上传文件："+uploadFileName);
            // 获取桌面目录
            String desktopPath = userHome + "/Desktop/";
            // 创建一个目标文件对象
            File destFile = new File(desktopPath + uploadFileName);
            // 将上传的文件写入到目标文件
            file.transferTo(destFile);
            // 文件上传成功
            return "Upload successful!";
        }
        return "文件上传失败";
    }
}
