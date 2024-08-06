package org.example.service.impl;

import org.example.common.HttpResult;
import org.example.enums.DynamicTypeEnum;
import org.example.param.PublishDynamicParam;
import org.example.service.DynamicManage;
import org.springframework.stereotype.Service;

/**
 * @Author 李志豪
 * @Date 2024/7/29 22:46
 */
@Service
public class BasicDynamicService implements DynamicManage {

    @Override
    public HttpResult dealDynamicPublishRequset(PublishDynamicParam param) {

        System.out.println("处理基础动态");
        return null;
    }

    @Override
    public Integer getType() {
        return DynamicTypeEnum.BASIC.getType();
    }
}
