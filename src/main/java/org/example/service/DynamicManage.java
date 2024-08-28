package org.example.service;

import org.example.common.HttpResult;
import org.example.param.PublishDynamicParam;

/**
 * @Author 李志豪
 * @Date 2024/7/29 22:45
 */
public interface DynamicManage {

    public HttpResult dealDynamicPublishRequset(PublishDynamicParam param);

    public HttpResult dealDynamicExtContent(String extContent);

    public Integer getType();
}
