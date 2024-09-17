package org.example.message;

import org.checkerframework.checker.units.qual.A;
import org.example.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author 李志豪
 * @Date 2024/9/17 21:57
 */
@Service
public class MessageConsumer {

    ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private JedisUtil jedisUtil;

    @PostConstruct
    public void consumerMessage(){
        System.out.println("此时项目启动，被注解的方法，有没有执行");
        executorService.submit(()->dealAsyncListMessage());
    }

    public void dealAsyncListMessage(){
        //lpop后进先出  栈的模式
        //rpop先进先出  队列的模式
        while(true){
            if(jedisUtil.llen(MessageConstant.ASYNC_LIST_NAME)>3){
                String message = jedisUtil.rpop(MessageConstant.ASYNC_LIST_NAME);
                System.out.println("消费者从异步队列中获取消息："+message);
            }
        }
    }
}
