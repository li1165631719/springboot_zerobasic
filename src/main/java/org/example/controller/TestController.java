package org.example.controller;

import org.example.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 李志豪
 * @Date 2024/5/26 18:52
 */
@RestController
@RequestMapping("test")
public class TestController {

    final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    TestService testService;

    @GetMapping("/test1")
    String test1(){
        return "hello world!欢迎学习springboot项目！";
    }

    @PostMapping("/test/mysql")
    public String testMySql(@RequestParam("content") String content){

        return "success :" + testService.addTestContent(content);
    }

    @PostMapping("/test/mysql/{id}")
    public String testMysqlQuery(@PathVariable("id") String id){
        logger.info("查询test的content，查询id："+id);
        return testService.testMysqlQuery(id);
    }
}
