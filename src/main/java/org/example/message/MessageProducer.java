package org.example.message;

import org.example.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 李志豪
 * @Date 2024/9/17 21:44
 */
@Service
public class MessageProducer {

    @Autowired
    private JedisUtil jedisUtil;
    public void produceMessage(String message){
        jedisUtil.lpush(MessageConstant.ASYNC_LIST_NAME,message);

    }
}
