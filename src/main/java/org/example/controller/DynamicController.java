package org.example.controller;

import org.checkerframework.checker.units.qual.A;
import org.example.common.HttpResult;
import org.example.message.MessageConstant;
import org.example.param.PublishDynamicParam;
import org.example.param.QueryDynamicPageParam;
import org.example.service.DynamicService;
import org.example.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return service.publishDynamic(param);
    }

    @PostMapping("/query/page")
    public HttpResult queryDynamicPage(@RequestBody QueryDynamicPageParam param){
        return service.queryDynamicPage(param);
    }




}
