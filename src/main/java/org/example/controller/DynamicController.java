package org.example.controller;

import org.example.common.HttpResult;
import org.example.param.PublishDynamicParam;
import org.example.service.DynamicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 李志豪
 * @Date 2024/7/29 22:38
 */

@RestController
@RequestMapping("dynamic")
public class DynamicController {


    @Autowired
    private DynamicService service;

    @PostMapping("/publish")
    public HttpResult publishDynamic(@RequestBody PublishDynamicParam param){

        return null;
    }
}
