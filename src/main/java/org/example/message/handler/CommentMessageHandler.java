package org.example.message.handler;

import org.example.dto.CommentDTO;
import org.example.dto.DynamicDTO;
import org.example.dto.InformDTO;
import org.example.enums.EntityTypeEnum;
import org.example.mapper.CommentMapper;
import org.example.mapper.DynamicMapper;
import org.example.mapper.InformMapper;
import org.example.message.AsyncMessageDTO;
import org.example.message.MessageConsumer;
import org.example.message.AsyncMessageDTO;
import org.example.message.MessageManage;
import org.example.message.MessageTypeEnum;
import org.example.message.dto.CommentMessageDTO;
import org.example.util.CommonUtil;
import org.example.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @Author 李志豪
 * @Date 2024/9/22 16:51
 */
@Service
public class CommentMessageHandler implements MessageManage {

    Logger logger = LoggerFactory.getLogger(CommentMessageHandler.class);

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    DynamicMapper dynamicMapper;

    @Autowired
    InformMapper informMapper;

    @Override
    public void dealMessage(AsyncMessageDTO asyncMessageDTO) {

        logger.info("开始处理评论产生的消息：{}", JsonUtils.objectToJson(asyncMessageDTO));
        /**
         * TODO 1、判断是否是对动态进行评论、动态的评论增加；2、判断是否是对评论进行回复，如果是，需要通知被回复的人
         * TODO 作业：1、设置消息表；2、设计消息相关的接口；3、将当前消息处理一下
         */
        String message = asyncMessageDTO.getMessage();
        //保证DTO的责任单一性，防止被其他人篡改
        CommentMessageDTO commentMessageDTO = JsonUtils.jsonToPojo(message,CommentMessageDTO.class);
        //查数据库确保获取评论实体为最新数据
        CommentDTO commentDTO = commentMapper.queryCommentById(commentMessageDTO.getId());

        //更新动态评论数量
        DynamicDTO dynamicDTO = dynamicMapper.queryDynamicById(commentDTO.getDynamicId());
        dynamicDTO.setCommentCount(dynamicDTO.getCommentCount() + 1);
        dynamicMapper.updateCommentCountById(dynamicDTO);

        /**
         * TODO 消息主体可以添加状态--已读/未读
         */

        Integer entityType = commentDTO.getEntityType();
        InformDTO informDTO = null;

        if(entityType.equals(EntityTypeEnum.DYNAMIC.getType())){
            informDTO = new InformDTO();
            informDTO.setId(CommonUtil.generateUUID());
            informDTO.setSendUserId(commentDTO.getUserId());
            informDTO.setTakeUserId(dynamicDTO.getUserId());
            informDTO.setContent(commentDTO.getContent());
            informDTO.setEntityId(dynamicDTO.getId());
            informDTO.setEntityType(EntityTypeEnum.DYNAMIC.getType());

            //保存--insert
            informMapper.addInform(informDTO);
        }

        if(entityType.equals(EntityTypeEnum.COMMENT.getType())){
            //通知被评论的主体所属用户
            CommentDTO commentTemp =  commentMapper.queryCommentById(commentDTO.getEntityId());
            informDTO = new InformDTO();
            informDTO.setId(CommonUtil.generateUUID());
            informDTO.setSendUserId(commentDTO.getUserId());
            informDTO.setTakeUserId(commentTemp.getUserId());
            informDTO.setContent(commentDTO.getContent());
            informDTO.setEntityId(commentTemp.getId());
            informDTO.setEntityType(EntityTypeEnum.COMMENT.getType());

            //保存--insert
            informMapper.addInform(informDTO);

            //通知被评论的动态所属用户
            DynamicDTO dynamicTemp = dynamicMapper.queryDynamicById(commentTemp.getDynamicId());
            informDTO.setId(CommonUtil.generateUUID());
            informDTO.setSendUserId(commentDTO.getUserId());
            informDTO.setTakeUserId(dynamicTemp.getUserId());
            informDTO.setContent(commentDTO.getContent());
            informDTO.setEntityId(dynamicTemp.getId());
            informDTO.setEntityType(EntityTypeEnum.DYNAMIC.getType());

            //保存--insert
            informMapper.addInform(informDTO);
        }



    }

    @Override
    public String getMessageType() {
        return MessageTypeEnum.COMMENT_MESSAGE.getType();
    }
}
