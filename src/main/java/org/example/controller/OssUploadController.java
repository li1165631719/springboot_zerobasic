package org.example.controller;

import org.example.common.HttpResult;
import org.example.util.QiniuPictureServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author 李志豪
 * @Date 2024/8/6 23:15
 */
@RestController
@RequestMapping("oss")
public class OssUploadController {

    @Autowired
    private QiniuPictureServiceUtils qiniuPictureServiceUtils;

    @PostMapping("/upload")
    public HttpResult uploadImg(@RequestParam("file")MultipartFile file){
        try {
            return qiniuPictureServiceUtils.saveImage(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
