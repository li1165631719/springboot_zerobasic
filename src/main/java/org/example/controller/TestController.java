package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 李志豪
 * @Date 2024/5/26 18:52
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("/test1")
    String test1(){
        return "hello world!欢迎学习springboot项目！";
    }
}
