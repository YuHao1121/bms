package org.example.controller;

import org.example.entity.Result;
import org.example.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class FileUploadController {

    @Autowired
    private AliOssUtil ossUtil;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {

        String originalFilename = file.getOriginalFilename();

        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //调用aliOss
        String url =  ossUtil.upload(filename,file.getInputStream());
        return Result.success(url);
    }
}
