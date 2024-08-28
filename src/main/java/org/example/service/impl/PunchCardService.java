package org.example.service.impl;

import org.example.common.HttpResult;
import org.example.constant.DynamicConstant;
import org.example.dto.DynamicDTO;
import org.example.dto.PunchCardDTO;
import org.example.enums.DynamicStatusEnum;
import org.example.enums.DynamicTypeEnum;
import org.example.local.HostHolder;
import org.example.mapper.DynamicMapper;
import org.example.param.PublishDynamicParam;
import org.example.param.PunchCardParam;
import org.example.service.DynamicManage;
import org.example.util.CommonUtil;
import org.example.util.JsonUtils;
import org.example.vo.PunchCardVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @Author 李志豪
 * @Date 2024/7/29 22:46
 */
@Service
public class PunchCardService implements DynamicManage {

    final static Logger logger = LoggerFactory.getLogger(PunchCardService.class);

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private DynamicMapper dynamicMapper;

    @Override
    public HttpResult dealDynamicPublishRequset(PublishDynamicParam param) {
        String userId = hostHolder.getUser().getUserId();
        logger.info("开始打卡发布，userId:{},请求参数:{}", userId,JsonUtils.objectToJson(param));
        PunchCardParam punchCardParam = param.getPunchCardParam();
        Date punchCardDate = punchCardParam.getPunchCardDate();
        String punchCardContent = punchCardParam.getPunchCardContent();

        if(ObjectUtils.isEmpty(punchCardDate)){
            logger.info("开始打卡发布，打卡时间为空，请求参数为：{}",JsonUtils.objectToJson(param));
            return HttpResult.fail();
        }

        if (!CommonUtil.getNowDayFormat(new Date()).equals(CommonUtil.getNowDayFormat(punchCardDate))){
            logger.info("开始打卡发布，打卡时间不正常，请求参数为：{}",JsonUtils.objectToJson(param));
            return HttpResult.fail();
        }

        if (StringUtils.isEmpty(punchCardContent)){
            logger.info("开始打卡发布，打卡内容为空，请求参数为：{}",JsonUtils.objectToJson(param));
            return HttpResult.fail();
        }
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
        PunchCardDTO punchCardDTO = new PunchCardDTO();
        punchCardDTO.setId(CommonUtil.generateUUID());
        punchCardDTO.setPunCardDate(punchCardDate);
        punchCardDTO.setPunchCardContent(punchCardContent);
        punchCardDTO.setDynamicId(dynamicDTO.getId());

        dynamicDTO.setExtContent(JsonUtils.objectToJson(punchCardDTO));

        dynamicMapper.insertDynamic(dynamicDTO);

        return HttpResult.ok();
    }

    @Override
    public HttpResult dealDynamicExtContent(String extContent) {
        return new HttpResult<>(JsonUtils.jsonToPojo(extContent, PunchCardVO.class));
    }

    @Override
    public Integer getType() {
        return DynamicTypeEnum.PUNCH_CARD.getType();
    }
}
