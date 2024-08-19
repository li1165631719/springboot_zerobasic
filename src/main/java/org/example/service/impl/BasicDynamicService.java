package org.example.service.impl;

import org.example.common.HttpResult;
import org.example.constant.DynamicConstant;
import org.example.dto.DynamicDTO;
import org.example.enums.DynamicStatusEnum;
import org.example.enums.DynamicTypeEnum;
import org.example.local.HostHolder;
import org.example.mapper.DynamicMapper;
import org.example.param.PublishDynamicParam;
import org.example.service.DynamicManage;
import org.example.util.CommonUtil;
import org.example.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author 李志豪
 * @Date 2024/7/29 22:46
 */
@Service
public class BasicDynamicService implements DynamicManage {

    final static Logger logger = LoggerFactory.getLogger(BasicDynamicService.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    private DynamicMapper dynamicMapper;

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
            dynamicDTO.setType(param.getType());
            dynamicDTO.setStatus(DynamicStatusEnum.NORMAL.getStatus());
            dynamicDTO.setCommentCount(DynamicConstant.DEFAULT_DYNAMIC_COMMENT_COUNT);
            dynamicDTO.setCreatedDate(new Date());
            dynamicDTO.setUpdateDate(new Date());
            dynamicMapper.insertDynamic(dynamicDTO);
            return HttpResult.ok();
        } catch (Exception e) {
            logger.error("开始发布基础的图文动态一次，userId:{},请求参数：{}", userId, JsonUtils.objectToJson(param));
            e.printStackTrace();
            return HttpResult.fail();
        }

    }

    @Override
    public Integer getType() {
        return DynamicTypeEnum.BASIC.getType();
    }
}
