package com.woniuxy.file.controller;

import com.woniuxy.common.utils.ResponseResult;
import com.woniuxy.file.service.AliyunOSSService;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    AliyunOSSService aliyunOSSService;

    @PostMapping("/upload")
    public ResponseResult<String> upload(MultipartFile file) {
        File newFile = null;
        try {
            newFile = new File(file.getOriginalFilename());
            FileUtils.copyInputStreamToFile(file.getInputStream(), newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String upload = aliyunOSSService.upload(newFile);
        newFile.delete();
        return new ResponseResult<>(upload);
    }
}
