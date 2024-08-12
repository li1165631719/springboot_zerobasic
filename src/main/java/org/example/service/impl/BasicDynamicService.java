package org.example.service.impl;

import org.apache.catalina.Host;
import org.example.common.HttpResult;
import org.example.constant.DynamicContant;
import org.example.dto.DynamicDTO;
import org.example.enums.DynamicStatusEnum;
import org.example.enums.DynamicTypeEnum;
import org.example.local.HostHolder;
import org.example.param.PublishDynamicParam;
import org.example.service.DynamicManage;
import org.example.util.CommonUtil;
import org.example.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 李志豪
 * @Date 2024/7/29 22:46
 */
@Service
public class BasicDynamicService implements DynamicManage {

    final static Logger logger = LoggerFactory.getLogger(BasicDynamicService.class);

    @Autowired
    HostHolder hostHolder;

    @Override
    public HttpResult dealDynamicPublishRequset(PublishDynamicParam param) {
        String userId = hostHolder.getUser().getUserId();
        logger.info("开始发布基础的图文动态，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));


        try {

            DynamicDTO dynamicDTO = new DynamicDTO();
            dynamicDTO.setId(CommonUtil.generateUUID());
            dynamicDTO.setUserId(userId);
            dynamicDTO.setTitle(param.getTitle());
            dynamicDTO.setContent(param.getContent());
            dynamicDTO.setImgArray(JsonUtils.objectToJson(param.getImgs()));
            dynamicDTO.setStatus(DynamicStatusEnum.NORMAL.getStatus());
            dynamicDTO.setCommentCount(DynamicContant.DEFAULT_DYNAMIC_COMMENT_COUNT);


        } catch (Exception e) {

        }
        System.out.println("处理基础动态");
        return null;
    }

    @Override
    public Integer getType() {
        return DynamicTypeEnum.BASIC.getType();
    }
}
