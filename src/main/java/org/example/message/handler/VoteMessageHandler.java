package org.example.message.handler;

import org.example.dto.DynamicDTO;
import org.example.dto.InformDTO;
import org.example.dto.VoteDTO;
import org.example.enums.DynamicTypeEnum;
import org.example.mapper.DynamicMapper;
import org.example.mapper.InformMapper;
import org.example.message.AsyncMessageDTO;
import org.example.message.MessageManage;
import org.example.message.MessageTypeEnum;
import org.example.message.dto.TakeVoteMessageDTO;
import org.example.util.CommonUtil;
import org.example.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 李志豪
 * @Date 2024/9/22 16:51
 */
@Service
public class VoteMessageHandler implements MessageManage {
    Logger logger = LoggerFactory.getLogger(VoteMessageHandler.class);

    @Autowired
    DynamicMapper dynamicMapper;

    @Autowired
    InformMapper informMapper;

    @Override
    public void dealMessage(AsyncMessageDTO asyncMessageDTO) {

        logger.info("开始处理投票产生的消息：{}", JsonUtils.objectToJson(asyncMessageDTO));

        String message = asyncMessageDTO.getMessage();

        TakeVoteMessageDTO takeVoteMessageDTO =JsonUtils.jsonToPojo(message, TakeVoteMessageDTO.class);

        String dynamicId = takeVoteMessageDTO.getDynamicId();

        DynamicDTO dynamicDTO = dynamicMapper.queryDynamicById(dynamicId);

        String extContent=dynamicDTO.getExtContent();

        VoteDTO voteDTO = JsonUtils.jsonToPojo(extContent,VoteDTO.class);

        InformDTO informDTO = new InformDTO();

        informDTO.setId(CommonUtil.generateUUID());
        informDTO.setSendUserId(asyncMessageDTO.getUserId());
        informDTO.setTakeUserId(dynamicDTO.getUserId());
        informDTO.setContent("您的投票动态，"+voteDTO.getVoteTitle()+"获得了"+asyncMessageDTO.getUserId()+"用户的认同");
        informDTO.setEntityId(dynamicDTO.getId());
        informDTO.setEntityType(DynamicTypeEnum.VOTE.getType());
        informMapper.addInform(informDTO);


    }

    @Override
    public String getMessageType() {
        return MessageTypeEnum.VOTE_MESSAGE.getType();
    }
}
