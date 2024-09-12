package org.example.service.impl;

import org.example.common.HttpResult;
import org.example.constant.DynamicConstant;
import org.example.dto.DynamicDTO;
import org.example.dto.VoteDTO;
import org.example.dto.VoteOptionDTO;
import org.example.enums.DynamicStatusEnum;
import org.example.enums.DynamicTypeEnum;
import org.example.exception.DynamicException;
import org.example.local.HostHolder;
import org.example.mapper.DynamicMapper;
import org.example.param.PublishDynamicParam;
import org.example.param.VoteOptionParam;
import org.example.param.VoteParam;
import org.example.service.DynamicManage;
import org.example.util.CommonUtil;
import org.example.util.JsonUtils;
import org.example.vo.PunchCardVO;
import org.example.vo.VoteVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 李志豪
 * @Date 2024/7/29 22:46
 */
@Service
public class VoteService implements DynamicManage {

    final static Logger logger = LoggerFactory.getLogger(VoteService.class);

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private DynamicMapper dynamicMapper;

    @Override
    public HttpResult dealDynamicPublishRequset(PublishDynamicParam param) {
        String userId = hostHolder.getUser().getUserId();
        logger.info("开始发布投票动态，userId:{},请求参数：{}",userId,param);
        VoteParam voteParam = param.getVoteParam();
        if(ObjectUtils.isEmpty(voteParam)){
            logger.info("开始发布投票动态，voteParam请求参数不能为空，userId:{},请求参数：{}",userId,param);
            return HttpResult.fail();
        }
        String voteTitle = voteParam.getVoteTitle();
        if(StringUtils.isEmpty(voteTitle)){
            logger.info("开始发布投票动态，投票标题不能为空，userId:{},请求参数：{}",userId,param);
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_VOTE_TITLE_IS_NULL);
        }
        List<VoteOptionParam> optionList = voteParam.getOptionList();
        if(ObjectUtils.isEmpty(optionList)||optionList.size()< DynamicConstant.DEFAULT_VOTE_OPTIONS_SIZE_MIN){
            logger.info("开始发布投票动态，投票选项不能为空，userId:{},请求参数：{}",userId,param);
            return HttpResult.generateHttpResult(DynamicException.DYNAMIC_VOTE_OPTIONLIST_IS_NULL);
        }

        for (VoteOptionParam item:optionList
             ) {
            String content = item.getContent();
            if(StringUtils.isEmpty(content)){
                logger.info("开始发布投票动态，投票选项内容不能为空，userId:{},请求参数：{}",userId,param);
                return HttpResult.fail();
            }
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


        VoteDTO voteDTO = new VoteDTO();
        voteDTO.setId(CommonUtil.generateUUID());
        voteDTO.setDynamicId(dynamicDTO.getId());
        voteDTO.setVoteTitle(voteTitle);
        voteDTO.setVoteTakeCount(DynamicConstant.DEFAULT_VOTE_COUNT_VALUE);
        List<VoteOptionDTO> voteOptionDTOS = new ArrayList<>();

        for (int i = 0; i < optionList.size(); i++) {
            VoteOptionDTO voteOptionDTO = new VoteOptionDTO();
            voteOptionDTO.setVoteId(voteDTO.getId());
            voteOptionDTO.setContent(optionList.get(i).getContent());
            voteOptionDTO.setId(CommonUtil.generateUUID());
            voteOptionDTO.setVoteCount(0);
            voteOptionDTO.setOptionOrder(i);
            voteOptionDTOS.add(voteOptionDTO);
        }

        voteDTO.setOptionList(voteOptionDTOS);


        dynamicDTO.setExtContent(JsonUtils.objectToJson(voteDTO));

        dynamicMapper.insertDynamic(dynamicDTO);


        return HttpResult.ok();
    }

    @Override
    public HttpResult dealDynamicExtContent(String extContent) {

        return new HttpResult<>(JsonUtils.jsonToPojo(extContent, VoteVO.class));
    }

    @Override
    public Integer getType() {
        return DynamicTypeEnum.VOTE.getType();
    }
}
