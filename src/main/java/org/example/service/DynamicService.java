package org.example.service;

import org.example.common.HttpResult;
import org.example.param.PublishDynamicParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 李志豪
 * @Date 2024/7/31 21:45
 */
@Service
public class DynamicService {

    @Autowired
    private List<DynamicManage> dynamicManageList;//通过Autowired将DynamicManage接口通过集合的方式全部返回回来

    public HttpResult publishDynamic(PublishDynamicParam param){

        Integer type = param.getType();

        dynamicManageList.stream()
                .filter(item->type.equals(item.getType()))
                .findFirst().get().dealDynamicPublishRequset(param);

        return  null;
    }


}
