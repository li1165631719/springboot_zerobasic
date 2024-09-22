package org.example.message.handler;

import org.example.message.MessageConsumer;
import org.example.message.MessageDTO;
import org.example.message.MessageManage;
import org.example.message.MessageTypeEnum;
import org.example.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author 李志豪
 * @Date 2024/9/22 16:51
 */
@Service
public class CommentMessageHandler implements MessageManage {

    Logger logger = LoggerFactory.getLogger(CommentMessageHandler.class);

    @Override
    public void dealMessage(MessageDTO messageDTO) {

        logger.info("开始处理评论产生的消息：{}", JsonUtils.objectToJson(messageDTO));
        /**
         * TODO 1、判断是否是对动态进行评论、动态的评论增加；2、判断是否是对评论进行回复，如果是，需要通知被回复的人
         * TODO 作业：1、设置消息表；2、设计消息相关的接口；3、将当前消息处理一下
         */

    }

    @Override
    public String getMessageType() {
        return MessageTypeEnum.COMMENT_MESSAGE.getType();
    }
}
