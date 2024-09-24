package org.example.message;

import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.example.controller.LoginController;
import org.example.util.JedisUtil;
import org.example.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author 李志豪
 * @Date 2024/9/17 21:57
 */
@Service
public class MessageConsumer {

    Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    List<MessageManage> messageManages;

    @PostConstruct
    public void consumerMessage(){
        System.out.println("此时项目启动，被注解的方法，有没有执行");
        executorService.submit(()->dealAsyncListMessage());
    }

    public void dealAsyncListMessage(){
        //lpop后进先出  栈的模式
        //rpop先进先出  队列的模式
        while(true){
            if(jedisUtil.llen(MessageConstant.ASYNC_LIST_NAME)>0){
                String message = jedisUtil.rpop(MessageConstant.ASYNC_LIST_NAME);
                System.out.println("消费者从异步队列中获取消息："+message);
                logger.info("开始消费消息:{}",message);
                try{
                    AsyncMessageDTO asyncMessageDTO = JsonUtils.jsonToPojo(message, AsyncMessageDTO.class);
                    if(ObjectUtils.isEmpty(asyncMessageDTO)){
                        continue;
                    }
                    String type = asyncMessageDTO.getType();
                    //匹配消息处理器
                    messageManages.stream().filter(item->item.getMessageType().equals(type
                    )).findFirst().get().dealMessage(asyncMessageDTO);
                }catch (Exception e){
                    logger.info("消息处理发生异常：{}",message);
                    e.printStackTrace();
                }
            }
        }
    }
}
