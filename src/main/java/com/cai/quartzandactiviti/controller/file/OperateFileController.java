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
import java.util.ArrayList;
import java.util.List;

@Api(tags = "操作文件")
@RestController
@RequestMapping("/operate")
@Slf4j
public class OperateFileController {
    @Value("${file.upload-path}")
    private String uploadPath;

    /**
     * 上传文件
     * @param flowchart 流程图文件
     * @param picture 流程图
     * @return 返回上传结果
     */
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("flowchart") MultipartFile flowchart
            ,@RequestParam("picture") MultipartFile picture){
        // 处理上传的文件
        if (!flowchart.isEmpty()&&!picture.isEmpty()) {
            String uploadFlowchartName=flowchart.getOriginalFilename();
            String uploadPictureName=picture.getOriginalFilename();
            log.debug("上传路径：{},上传文件：{}",uploadPath,uploadFlowchartName);
            log.debug("上传路径：{},上传图片：{}",uploadPath,uploadPictureName);
            File destFile = new File(uploadPath + uploadFlowchartName);
            File destPicture = new File(uploadPath + uploadPictureName);
            // 将上传的文件写入到目标文件
            try {
                flowchart.transferTo(destFile);
                picture.transferTo(destPicture);
            } catch (IOException e) {
                log.warn("文件写入异常");
                throw new RuntimeException(e);
            }
            // 文件上传成功
            List<String> listFileName=new ArrayList<>();
            listFileName.add(uploadFlowchartName);
            listFileName.add(uploadFlowchartName);
            return Result.ok("file",listFileName);
        }
        return Result.error();
    }
}
